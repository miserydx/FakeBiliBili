package com.bilibili.widget.danmu.live;

import com.bilibili.widget.danmu.live.entity.*;

/**
 * <p>Methods in this class will be called on data received or disconnect.
 * Created by czp on 17-5-24.
 */
public interface ILiveDanMuCallback {
    /**
     * This method will be called on connect succeed.
     */
    //连接成功
    void onConnect();

    /**
     * This method will be called on disconnect include socket error and use of LiveDanMuReceiver.close().
     *
     * @see LiveDanMuReceiver
     */
    //连接断开
    void onDisconnect();

    /**
     * This method will be called on receive online count package.
     *
     * @param onlineCount the count of people who are watching live
     */
    //收到在线人数数据包
    void onOnlineCountPackage(int onlineCount);

    /**
     * On DanMu message package.
     */
    //收到弹幕消息数据包
    void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity);

    /**
     * On system message package.
     */
    //收到系统消息数据包
    void onSysMSGPackage(SysMSGEntity sysMSGEntity);

    /**
     * On send gift package.
     */
    //收到礼物数据包
    void onSendGiftPackage(SendGiftEntity sendGiftEntity);

    /**
     * On system gift package.
     */
    //收到系统礼物数据包
    void onSysGiftPackage(SysGiftEntity sysGiftEntity);

    /**
     * On welcome package.
     */
    //收到欢迎数据包
    void onWelcomePackage(WelcomeEntity welcomeEntity);

    /**
     * On welcome guard package.
     */
    //收到欢迎管理员数据包
    void onWelcomeGuardPackage(WelcomeGuardEntity welcomeGuardEntity);

    /**
     * On live package.
     */
    //直播开始
    void onLivePackage(LiveEntity liveEntity);

    /**
     * On preparing package.
     */
    //直播结束
    void onPreparingPackage(PreparingEntity preparingEntity);

    /**
     * On room admins package.
     */
    void onRoomAdminsPackage(RoomAdminsEntity roomAdminsEntity);
}
