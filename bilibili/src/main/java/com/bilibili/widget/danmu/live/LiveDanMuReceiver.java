package com.bilibili.widget.danmu.live;

import android.support.annotation.WorkerThread;

import com.bilibili.util.CommonConsumer;
import com.bilibili.util.RxJavaUtil;
import com.team.ijkplayer.player.DXMediaManager;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.List;
//import java.util.Optional;
import java.util.Vector;

import io.reactivex.annotations.NonNull;

/**
 * DanMu receive API.
 * Created by czp on 17-5-24.
 */
public class LiveDanMuReceiver implements Closeable {
    private static final String CID_INFO_URL = "http://live.bilibili.com/api/player?id=cid:";
    private static final int LIVE_SERVER_PORT = 788;
    private static final int SOCKET_TIMEOUT = 40 * 1000;

    private static LiveDanMuReceiver instance;
    private int roomId;
    private Long random;
    private Integer roomURL;
    private String urlString;
    private URL url;
    private Socket socket;
    private List<ILiveDanMuCallback> callbacks = new Vector<>();
    private Boolean printDebugInfo = false;
    private Thread heartBeatThread;
    private Thread callbackDispatchThread;

    /**
     * Class constructor, need room id.
     *
     * @param roomId the id of room
     */
    private LiveDanMuReceiver(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Class constructor, need URL of room in String.
     *
     * @param url the URL of room in String
     */
    private LiveDanMuReceiver(String url) {
        this.urlString = url;
    }

    /**
     * Class constructor, need URL of room.
     *
     * @param url the URL of room
     */
    private LiveDanMuReceiver(URL url) {
        this.url = url;
    }

    private LiveDanMuReceiver() {

    }

    public static synchronized LiveDanMuReceiver getInstance() {
        if (instance == null) {
            instance = new LiveDanMuReceiver();
        }
        return instance;
    }

    Socket getSocket() {
        return socket;
    }

    /**
     * Add callback, it will be invoked on data incoming or lost connection.
     *
     * @param liveDanMuCallback the class which implements from ILiveDanMuCallback
     * @return self reference
     */
    public LiveDanMuReceiver addCallback(ILiveDanMuCallback liveDanMuCallback) {
        callbacks.add(liveDanMuCallback);
        return this;
    }

    /**
     * Remove callback.
     *
     * @param liveDanMuCallback the class which implements from ILiveDanMuCallback
     * @return is remove operation success
     */
    public boolean removeCallback(ILiveDanMuCallback liveDanMuCallback) {
        return callbacks.remove(liveDanMuCallback);
    }

    /**
     * Connect to live server.
     *
     * @return self reference
     * @throws IOException              when socket error
     * @throws IllegalArgumentException when room id invalid
     */
    public LiveDanMuReceiver connect(int id) throws IOException, IllegalArgumentException {
        this.roomId = id;
        RxJavaUtil.runOnWorkThread(new CommonConsumer() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                //得到房间号
//                if (urlString != null) {
//                    url = new URL(urlString);
//                }
//                if (url != null) {
//                    ScriptEntity scriptEntity = Utils.resolveScriptPartInHTML(url);
//                    roomId = scriptEntity.roomId;
//                    random = scriptEntity.random;
//                    roomURL = scriptEntity.roomURL;
//                }

                //获得服务器地址
                String serverAddress;
                try {
                    InputStream inputStream = new URL(CID_INFO_URL + roomId).openStream();
                    serverAddress = Jsoup.parse(inputStream,
                            "UTF-8",
                            "",
                            Parser.xmlParser())
                            .select("server").first()
                            .text();
                } catch (FileNotFoundException e) {
                    throw new IllegalArgumentException("Invalid RoomID");
                } catch (NullPointerException e) {
                    throw new SocketException("Network error");
                }

                socket = new Socket(serverAddress, LIVE_SERVER_PORT);
                socket.setSoTimeout(SOCKET_TIMEOUT);
                OutputStream outputStream = socket.getOutputStream();

                //发送进房数据包
                outputStream.write(PackageRepository.getJoinPackage(roomId));
                outputStream.flush();

                InputStream inputStream = socket.getInputStream();

                if (!PackageRepository.readAndValidateJoinSuccessPackage(inputStream)) {
                    socket.close();
                    throw new SocketException("Join live channel failed");
                }

                //定时发送心跳包
                heartBeatThread = new Thread(new HeartBeatRunnable(outputStream));
                heartBeatThread.start();
                //启动回调分发线程
                callbackDispatchThread = new Thread(new CallbackDispatchRunnable(LiveDanMuReceiver.this, inputStream, callbacks, printDebugInfo));
                callbackDispatchThread.start();

                //由于回调本身可能继续添加回调, 因此需要按下标循环处理直到不再产生新回调. 直接 forEach 会导致 ConcurrentModificationException
                for (int i = 0; i < callbacks.size(); i++) {
                    callbacks.get(i).onConnect();
                }
            }
        });

        return this;
    }

    /**
     * Set print debug info, default is false.
     *
     * @param printDebugInfo true for print, false for not
     * @return self reference
     */
    public LiveDanMuReceiver setPrintDebugInfo(Boolean printDebugInfo) {
        this.printDebugInfo = printDebugInfo;
        return this;
    }

    /**
     * Close the connect and interrupt thread.
     *
     * @throws IOException inherit from Closeable
     * @see Closeable
     */
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (heartBeatThread != null) {
            try {
                heartBeatThread.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        callbacks.clear();
    }

    /**
     * Wait until CallbackDispatchThread exit.
     */
    public void waitUntilCallbackDispatchThreadExit() {
        if (callbackDispatchThread != null) {
            try {
                callbackDispatchThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    /**
//     * Get URL of room.
//     */
//    public Optional<URL> getUrl() {
//        return url == null ? Optional.empty() : Optional.of(url);
//    }
//
//    /**
//     * Get ROOMID
//     */
//    public int getRoomId() {
//        return roomId;
//    }
//
//    /**
//     * Get DANMU_RND.
//     */
//    public Optional<Long> getRandom() {
//        return random == null ? Optional.empty() : Optional.of(random);
//    }
//
//    /**
//     * Get ROOMURL.
//     */
//    public Optional<Integer> getRoomURL() {
//        return roomURL == null ? Optional.empty() : Optional.of(roomURL);
//    }
//
//    /**
//     * Get is socket connected.
//     */
//    public boolean isConnected() {
//        return socket != null && socket.isConnected();
//    }
}
