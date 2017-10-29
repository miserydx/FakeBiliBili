package com.team.ijkplayer.player;

import java.util.Map;

/**
 * Created by miserydx on 17/9/26.
 */

public class IjkDataSource {

    private String url;

    private Map<String, String> mapHeadData;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getMapHeadData() {
        return mapHeadData;
    }

    public void setMapHeadData(Map<String, String> mapHeadData) {
        this.mapHeadData = mapHeadData;
    }
}
