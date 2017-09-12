package com.bilibili.widget.danmu.live;


import com.bilibili.widget.danmu.live.entity.*;

/**
 * Abstract class implement from ILiveDanMuCallback.
 * Created by czp on 17-6-6.
 */
public abstract class LiveDanMuCallbackAdapter implements ILiveDanMuCallback {
    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void onOnlineCountPackage(int onlineCount) {

    }

    @Override
    public void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity) {

    }

    @Override
    public void onSysMSGPackage(SysMSGEntity sysMSGEntity) {

    }

    @Override
    public void onSendGiftPackage(SendGiftEntity sendGiftEntity) {

    }

    @Override
    public void onSysGiftPackage(SysGiftEntity sysGiftEntity) {

    }

    @Override
    public void onWelcomePackage(WelcomeEntity welcomeEntity) {

    }

    @Override
    public void onWelcomeGuardPackage(WelcomeGuardEntity welcomeGuardEntity) {

    }

    @Override
    public void onLivePackage(LiveEntity liveEntity) {

    }

    @Override
    public void onPreparingPackage(PreparingEntity preparingEntity) {

    }

    @Override
    public void onRoomAdminsPackage(RoomAdminsEntity roomAdminsEntity) {

    }
}
