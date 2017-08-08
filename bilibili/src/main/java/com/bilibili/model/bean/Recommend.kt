package com.bilibili.model.bean

/**
 * Created by miserydx on 17/8/8.
 */
data class AppIndex(var title: String,
                    var cover: String,
                    var uri: String,
                    var param: String,
                    var goto: String,
                    var desc: String,
                    var play: Int,
                    var danmaku: Int,
                    var reply: Int,
                    var favorite: Int,
                    var coin: Int,
                    var share: Int,
                    var idx: Int,
                    var banner_item: List<Banner_item>,
                    var tid: Int,
                    var tname: String,
                    var dislike_reasons: List<Dislike_reasons>,
                    var ctime: Int,
                    var duration: Int,
                    var mid: Int,
                    var name: String,
                    var face: String,
                    var request_id: String,
                    var src_id: Int,
                    var is_ad_loc: Boolean,
                    var client_ip: String,
                    var ad_index: Int)

data class Dislike_reasons(var reason_id: Int,
                           var reason_name: String)

data class Banner_item(var id: Int,
                       var title: String,
                       var image: String,
                       var hash: String,
                       var uri: String,
                       var request_id: String,
                       var creative_id: Int,
                       var src_id: Int,
                       var is_ad: Boolean,
                       var is_ad_loc: Boolean,
                       var ad_cb: String,
                       var client_ip: String,
                       var server_type: Int,
                       var resource_id: Int,
                       var index: Int,
                       var cm_mark: Int)