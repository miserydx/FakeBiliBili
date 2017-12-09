package com.bilibili.widget.danmu.live;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.bilibili.widget.danmu.live.entity.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
//import java.util.function.Consumer;

/**
 * Created by czp on 17-5-24.
 */
class CallbackDispatchRunnable implements Runnable {
//    private static final Logger log = LoggerFactory.getLogger(CallbackDispatchRunnable.class);

    private LiveDanMuReceiver liveDanMuReceiver;
    private InputStream inputStream;
    private List<ILiveDanMuCallback> callbacks;
    private Boolean printDebugInfo = false;
    private byte[] jsonBytes;

    CallbackDispatchRunnable(LiveDanMuReceiver liveDanMuReceiver, InputStream inputStream, List<ILiveDanMuCallback> callbacks, Boolean printDebugInfo) {
        this.liveDanMuReceiver = liveDanMuReceiver;
        this.inputStream = inputStream;
        this.callbacks = callbacks;
        this.printDebugInfo = printDebugInfo;
    }

    private void dispatch() throws Exception {
        byte[] packageBytes = PackageRepository.readNextPackage(inputStream);
        //如果没有回调函数直接开始监听下一个数据包
        if (callbacks.size() == 0) {
            return;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(packageBytes);
        byteBuffer.position(PackageRepository.PACKAGE_LENGTH_BYTES_LENGTH);
        byte[] protocolBytes = new byte[PackageRepository.PACKAGE_PROTOCOL_BYTES_LENGTH];
        byteBuffer.get(protocolBytes);
//        Consumer<ILiveDanMuCallback> consumer = null;
        DanMuProcess<ILiveDanMuCallback> processer = null;
        if (Arrays.equals(protocolBytes, PackageRepository.DAN_MU_DATA_PACKAGE_PROTOCOL_BYTES)) {    //json数据包
            jsonBytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(jsonBytes);
            if (printDebugInfo) {
//                log.debug(new String(jsonBytes));
                Log.d("danmu", new String(jsonBytes));
            }
            String cmd = ((JSONEntity) JSON.parseObject(jsonBytes, JSONEntity.class)).cmd;
            switch (cmd) {
                case "LIVE": {
//                    processer = iLiveDanMuCallback -> iLiveDanMuCallback.onLivePackage(JSON.parseObject(jsonBytes, LiveEntity.class));
                    processer = new DanMuProcess<ILiveDanMuCallback>() {
                        @Override
                        public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                            iLiveDanMuCallback.onLivePackage((LiveEntity) JSON.parseObject(jsonBytes, LiveEntity.class));
                        }
                    };
                }
                break;
                case "PREPARING": {
//                    processer = iLiveDanMuCallback -> iLiveDanMuCallback.onPreparingPackage(JSON.parseObject(jsonBytes, PreparingEntity.class));
                    processer = new DanMuProcess<ILiveDanMuCallback>() {
                        @Override
                        public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                            iLiveDanMuCallback.onPreparingPackage((PreparingEntity) JSON.parseObject(jsonBytes, PreparingEntity.class));
                        }
                    };
                }
                break;
                case "DANMU_MSG": {
//                    processer = iLiveDanMuCallback -> iLiveDanMuCallback.onDanMuMSGPackage(JSON.parseObject(jsonBytes, DanMuMSGEntity.class));
                    processer = new DanMuProcess<ILiveDanMuCallback>() {
                        @Override
                        public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                            iLiveDanMuCallback.onDanMuMSGPackage((DanMuMSGEntity) JSON.parseObject(jsonBytes, DanMuMSGEntity.class));
                        }
                    };
                }
                break;
                case "SYS_MSG": {
//                    processer = iLiveDanMuCallback -> iLiveDanMuCallback.onSysMSGPackage(JSON.parseObject(jsonBytes, SysMSGEntity.class));
                    processer = new DanMuProcess<ILiveDanMuCallback>() {
                        @Override
                        public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                            iLiveDanMuCallback.onSysMSGPackage((SysMSGEntity) JSON.parseObject(jsonBytes, SysMSGEntity.class));
                        }
                    };
                }
                break;
                case "SEND_GIFT": {
//                    processer = iLiveDanMuCallback -> iLiveDanMuCallback.onSendGiftPackage(JSON.parseObject(jsonBytes, SendGiftEntity.class));
                    processer = new DanMuProcess<ILiveDanMuCallback>() {
                        @Override
                        public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                            iLiveDanMuCallback.onSendGiftPackage((SendGiftEntity) JSON.parseObject(jsonBytes, SendGiftEntity.class));
                        }
                    };
                }
                break;
                case "SYS_GIFT": {
//                    processer = iLiveDanMuCallback -> iLiveDanMuCallback.onSysGiftPackage(JSON.parseObject(jsonBytes, SysGiftEntity.class));
                    processer = new DanMuProcess<ILiveDanMuCallback>() {
                        @Override
                        public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                            iLiveDanMuCallback.onSysGiftPackage((SysGiftEntity) JSON.parseObject(jsonBytes, SysGiftEntity.class));
                        }
                    };
                }
                break;
                case "WELCOME": {
//                    processer = iLiveDanMuCallback -> iLiveDanMuCallback.onWelcomePackage(JSON.parseObject(jsonBytes, WelcomeEntity.class));
                    processer = new DanMuProcess<ILiveDanMuCallback>() {
                        @Override
                        public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                            iLiveDanMuCallback.onWelcomePackage((WelcomeEntity) JSON.parseObject(jsonBytes, WelcomeEntity.class));
                        }
                    };
                }
                break;
                case "WELCOME_GUARD": {
//                    processer = iLiveDanMuCallback -> iLiveDanMuCallback.onWelcomeGuardPackage(JSON.parseObject(jsonBytes, WelcomeGuardEntity.class));
                    processer = new DanMuProcess<ILiveDanMuCallback>() {
                        @Override
                        public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                            iLiveDanMuCallback.onWelcomeGuardPackage((WelcomeGuardEntity) JSON.parseObject(jsonBytes, WelcomeGuardEntity.class));
                        }
                    };
                }
                break;
                case "ROOM_ADMINS": {
//                    processer = iLiveDanMuCallback -> iLiveDanMuCallback.onRoomAdminsPackage(JSON.parseObject(jsonBytes, RoomAdminsEntity.class));
                    processer = new DanMuProcess<ILiveDanMuCallback>() {
                        @Override
                        public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                            iLiveDanMuCallback.onRoomAdminsPackage((RoomAdminsEntity) JSON.parseObject(jsonBytes, RoomAdminsEntity.class));
                        }
                    };
                }
                default: {
                    if (printDebugInfo) {
//                        log.error("Unknown json above");
                    }
                }
            }
        } else if (Arrays.equals(protocolBytes, PackageRepository.ONLINE_COUNT_PACKAGE_PROTOCOL_BYTES)) {    //在线人数数据包
            final int onlineCount = byteBuffer.getInt();
            if (printDebugInfo) {
//                log.debug("Viewers: " + onlineCount);
            }
//            processer = iLiveDanMuCallback -> iLiveDanMuCallback.onOnlineCountPackage(onlineCount);
            processer = new DanMuProcess<ILiveDanMuCallback>() {
                @Override
                public void process(ILiveDanMuCallback iLiveDanMuCallback) {
                    iLiveDanMuCallback.onOnlineCountPackage(onlineCount);
                }
            };
        } else {    //未知数据包
            if (printDebugInfo) {
//                log.error("Unknown package below");
                Utils.printBytes(packageBytes);
            }
        }
        if (processer != null) {
            //fori 是为了避免 ConcurrentModificationException
            for (int i = 0; i < callbacks.size(); i++) {
                try {   //避免异常导致之后的所有回调被跳过
                    processer.process(callbacks.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                dispatch();
            } catch (IOException e) {   //socket关闭时退出
                if (liveDanMuReceiver.getSocket().isClosed()) {
                    break;
                }
            } catch (JSONException e) {
//                log.error("Wrong JSON: " + new String(jsonBytes));
                Log.e("danmu", "Wrong JSON: " + new String(jsonBytes));
                e.printStackTrace();
            } catch (Exception e) { //其他错误时显示错误信息并继续监听下一个数据包
                Log.e("danmu", "another wrong");
                e.printStackTrace();
            }
        }

        try {
            liveDanMuReceiver.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //fori 是为了避免 ConcurrentModificationException
            for (int i = 0; i < callbacks.size(); i++) {
                try {
                    callbacks.get(i).onDisconnect();
                } catch (Exception e) { //出错时执行下一个
                    e.printStackTrace();
                }
            }
        }
    }
}
