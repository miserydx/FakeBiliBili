package com.bilibili.model.bean.live;

/**
 * Created by Android_ZzT on 17/6/21.
 */

public class LiveResponse {

    private LiveRecommend liveRecommendResponse;

    private LiveCommon liveCommonResponse;

    public LiveResponse(LiveRecommend liveRecommendResponse, LiveCommon liveCommonResponse) {
        this.liveRecommendResponse = liveRecommendResponse;
        this.liveCommonResponse = liveCommonResponse;
    }

    public LiveRecommend getLiveRecommendResponse() {
        return liveRecommendResponse;
    }

    public void setLiveRecommendResponse(LiveRecommend liveRecommendResponse) {
        this.liveRecommendResponse = liveRecommendResponse;
    }

    public LiveCommon getLiveCommonResponse() {
        return liveCommonResponse;
    }

    public void setLiveCommonResponse(LiveCommon liveCommonResponse) {
        this.liveCommonResponse = liveCommonResponse;
    }
}
