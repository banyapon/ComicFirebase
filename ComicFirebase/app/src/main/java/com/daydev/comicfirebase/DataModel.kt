package com.daydev.comicfirebase

class DataModel {
    var title: String?=null
    var content: String?=null
    var thumbnail: String?=null
    var key:String?=null
    var photo:String?=null

    constructor(title: String?, content: String?, thumbnail: String?, key: String?, photo: String?) {
        this.title = title
        this.content = content
        this.thumbnail = thumbnail
        this.key = key
        this.photo = photo
    }

    constructor()

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put("title", title!!)
        result.put("content", content!!)
        result.put("thumbnail", thumbnail!!)
        result.put("photo", photo!!)
        result.put("key", key!!)
        return result
    }
}