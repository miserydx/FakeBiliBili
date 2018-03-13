# FakeBiliBili

一款基于 **MVP+RxJava2+Retrofit+Dagger2+Butterknife+Fresco+MultitypeAdapter+MD** 的仿 B 站设计风格客户端

> 项目地址：[https://github.com/TeamNB/FakeBiliBili](https://github.com/TeamNB/FakeBiliBili)

目前完成**主页UI**以及**直播页**的部分UI（播放器和弹幕）

这个项目完全出自个人爱好，平常也是个b站重度用户。以后会不定期更新~大家多提意见多交流

觉得还不错就给个star吧 (￣▽￣)~*

### Version 1.1.0

* 架构搭建
* 分析 Api
* 完成主页界面

### Version 1.2.0
* 完成直播播放页面
 + 集成IjkPlayer并简易封装
 + 集成DanmakuFlameMaster和来自[czp3009](https://github.com/czp3009)的[bilibili-api](https://github.com/czp3009/bilibili-api)完成弹幕功能
* 一些开源库的更新

### 前期准备

* 反编译资源文件
* [mitmproxy](https://mitmproxy.org/)抓取 Api(支持https)
* 获得 B 站 signKey 签名逻辑
* 框架搭建

### 用到的开源库
* [RxJava2](https://github.com/ReactiveX/RxJava) 最coooooool的响应式编程框架
* [RxLifecycle](https://github.com/trello/RxLifecycle) 解决Rxjava内存泄漏
* [Retrofit](https://github.com/square/retrofit) 最流行的Android网络请求的框架
* [Okhttp3](https://github.com/square/okhttp) 和Retrofit搭配，项目中签名逻辑使用该库的拦截器模块实现
* [Dagger2](https://github.com/google/dagger) 门槛略高但功能强大的依赖注入框架
* [Butterknife](https://github.com/JakeWharton/butterknife) 绑定View的依赖注入框架
* [Eventbus](https://github.com/greenrobot/EventBus) 组件间通信
* [Fresco](https://github.com/facebook/fresco) 图片的加载和处理，功能强大，但相比其他图片库有点大
* [Fragmentation](https://github.com/YoKeyword/Fragmentation) 简化Fragment操作并填坑的库
* [Multitype](https://github.com/drakeet/MultiType) RecyclerView多类型库
* [IjkPlayer](https://github.com/Bilibili/ijkplayer) b站出品基于FFmpeg的开源视频播放框架
* [DanmakuFlameMaster](https://github.com/Bilibili/DanmakuFlameMaster) 烈焰弹幕使-b站开源弹幕解析绘制引擎项目

更多内容请看这里 [FakeBiliBili 系列 (启动篇)](http://www.jianshu.com/p/b3b9e13bd842)

### 成果展示

<table>
	<tr>
		<th>直播</th>
		<th>推荐</th>
		<th>追番</th>
		<th>分区</th>
		<th>侧滑</th>
	</tr>
	<tr>
		  <td>
			  <img src="http://image-repository.oss-cn-beijing.aliyuncs.com/main_page_live.png"/>
		  </td>
		  <td>
			  <img src="http://image-repository.oss-cn-beijing.aliyuncs.com/main_page_recommend.png"/>
		  </td>
		  <td>
			  <img src="http://image-repository.oss-cn-beijing.aliyuncs.com/main_page_bangumi.png"/>
      </td>
		  <td>
			  <img src="http://image-repository.oss-cn-beijing.aliyuncs.com/main_page_region.png"/>
		  </td>
		  <td>
			  <img src="http://image-repository.oss-cn-beijing.aliyuncs.com/main_page_drawer.png"/>
		  </td>
	</tr>
</table>

![直播页](http://image-repository.oss-cn-beijing.aliyuncs.com/live_page.gif)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
![数据列表](http://image-repository.oss-cn-beijing.aliyuncs.com/LoadMore.gif)
