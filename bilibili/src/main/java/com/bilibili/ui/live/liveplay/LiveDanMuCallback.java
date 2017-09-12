package com.bilibili.ui.live.liveplay;

import android.util.Log;

import com.bilibili.widget.danmu.live.ILiveDanMuCallback;
import com.bilibili.widget.danmu.live.entity.*;

import java.util.Date;
/**
 * Created by miserydx on 17/9/11.
 */
public class LiveDanMuCallback implements ILiveDanMuCallback {
//    private void print(String message, Object... objects) {
//        System.out.printf("[%s]", new Date());
//        System.out.printf(message + "\n", objects);
//    }

    private void print(String message, Object... objects) {
        String s = "";
        for (Object o : objects) {
            s += o.toString();
        }
        Log.d("danmu", message + "\n" + s);
    }

    @Override
    public void onConnect() {
        print("连接成功");
    }

    @Override
    public void onDisconnect() {
        print("断开连接");
    }

    @Override
    public void onOnlineCountPackage(int onlineCount) {
        print("在线人数: %d", onlineCount);
    }

    @Override
    public void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity) {
        print("[弹幕][%s] %s", danMuMSGEntity.getSenderNick(), danMuMSGEntity.getDanMuContent());
    }

    @Override
    public void onSysMSGPackage(SysMSGEntity sysMSGEntity) {
        print("[系统消息] %s %s", sysMSGEntity.msg, sysMSGEntity.url);
    }

    @Override
    public void onSendGiftPackage(SendGiftEntity sendGiftEntity) {
        print("[礼物] %s 赠送 %s %d 个", sendGiftEntity.data.uname, sendGiftEntity.data.giftName, sendGiftEntity.data.num);
    }

    @Override
    public void onSysGiftPackage(SysGiftEntity sysGiftEntity) {
        print("[系统礼物] %s", sysGiftEntity.msg);
    }

    @Override
    public void onWelcomePackage(WelcomeEntity welcomeEntity) {
        print("[欢迎] %s", welcomeEntity.data.uname);
    }

    @Override
    public void onWelcomeGuardPackage(WelcomeGuardEntity welcomeGuardEntity) {
        print("[欢迎管理员] %s", welcomeGuardEntity.data.username);
    }

    @Override
    public void onLivePackage(LiveEntity liveEntity) {
        print("直播开始");
    }

    @Override
    public void onPreparingPackage(PreparingEntity preparingEntity) {
        print("直播结束");
    }

    @Override
    public void onRoomAdminsPackage(RoomAdminsEntity roomAdminsEntity) {
        print("本房间有 %d 个管理员", roomAdminsEntity.uids.length);
    }
}

