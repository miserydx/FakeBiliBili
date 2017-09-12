package com.bilibili;

import android.app.ActivityManager;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.bilibili.di.component.ActivityComponent;
import com.bilibili.di.component.ApiComponent;
import com.bilibili.di.component.DaggerActivityComponent;
import com.bilibili.di.component.DaggerApiComponent;
import com.bilibili.di.component.DaggerFragmentComponent;
import com.bilibili.di.component.FragmentComponent;
import com.bilibili.di.module.ApiModule;
import com.bilibili.di.module.FragmentModule;
import com.bilibili.widget.CustomBitmapMemoryCacheParamsSupplier;
import com.common.app.ActivityLifecycleManager;
import com.common.app.AppComponent;
import com.common.app.DaggerAppComponent;
import com.common.util.Utils;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

public class App extends Application {

    private static App sInstance;

    public static synchronized App getInstance() {
        return sInstance;
    }

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleManager());
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.NONE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 在debug=false时，即线上环境时，上述异常会被捕获并回调ExceptionHandler
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 建议在该回调处上传至我们的Crash监测服务器
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
        initFresco();
        if (sAppComponent == null) {
            sAppComponent = DaggerAppComponent.create();
        }
        //初始化工具类
        Utils.init(this);
    }

    public void initFresco() {
        //当内存紧张时采取的措施
        MemoryTrimmableRegistry memoryTrimmableRegistry = NoOpMemoryTrimmableRegistry.getInstance();
        memoryTrimmableRegistry.registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
                Log.d("Fresco", String.format("onCreate suggestedTrimRatio : %d", suggestedTrimRatio));
                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
                        ) {
                    //清除内存缓存
                    Fresco.getImagePipeline().clearMemoryCaches();
                }
            }
        });
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setResizeAndRotateEnabledForNetwork(true)
//                .setBitmapMemoryCacheParamsSupplier(new DefaultBitmapMemoryCacheParamsSupplier((ActivityManager) getSystemService(ACTIVITY_SERVICE)))
//                .setBitmapMemoryCacheParamsSupplier(new DefaultEncodedMemoryCacheParamsSupplier())
                .setBitmapMemoryCacheParamsSupplier(new CustomBitmapMemoryCacheParamsSupplier((ActivityManager) getSystemService(ACTIVITY_SERVICE)))
                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, config);
    }

    public AppComponent getAppComponent() {
        return sAppComponent;
    }

    public ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .apiComponent(getApiComponent())
                .build();
    }

    public FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .apiComponent(getApiComponent())
                .fragmentModule(new FragmentModule())
                .build();
    }

    private ApiComponent getApiComponent() {
        return DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }
}
