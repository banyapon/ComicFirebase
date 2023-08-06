package com.daydev.comicfirebase

class LiveDataModel {
    var title: String?=null
    var content: String?=null
    var thumbnail: String?=null
    var key:String?=null

    constructor(title: String?, content: String?, thumbnail: String?, key: String?) {
        this.title = title
        this.content = content
        this.thumbnail = thumbnail
        this.key = key
    }

    constructor()

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put("title", title!!)
        result.put("content", content!!)
        result.put("thumbnail", thumbnail!!)
        result.put("key", key!!)
        return result
    }
}