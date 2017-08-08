package com.bilibili.model.bean

/**
 * Created by miserydx on 17/8/7.
 */
data class BangumiIndexFall(
        var cover: String,
        var cursor: Double,
        var desc: String,
        var id: Int,
        var is_new: Int,
        var link: String,
        var title: String,
        var type: Int,
        var wid: Int)

data class BangumiIndexPage(
        var ad: List<Ad>,
        var recommend_cn: Recommend_cn,
        var recommend_jp: Recommend_jp
)

data class Ad(
        var img: String,
        var index: Int,
        var link: String,
        var title: String
)

data class Recommend_cn(
        var foot: List<Foot>,
        var recommend: List<Recommend>
)

data class Recommend_jp(
        var foot: List<Foot>,
        var recommend: List<Recommend>
)

data class Foot(
        var cover: String,
        var cursor: Double,
        var desc: String,
        var id: Int,
        var is_new: Int,
        var link: String,
        var onDt: String,
        var title: String
)

data class Recommend(
        var badge: String,
        var cover: String,
        var favourites: String,
        var is_auto: Int,
        var is_finish: Int,
        var is_started: Int,
        var last_time: Int,
        var newest_ep_index: String,
        var pub_time: Int,
        var season_id: Int,
        var season_status: Int,
        var title: String,
        var total_count: Int,
        var watching_count: Int
)