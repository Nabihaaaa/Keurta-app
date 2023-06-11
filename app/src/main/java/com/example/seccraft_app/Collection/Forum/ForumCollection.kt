package com.example.seccraft_app.Collection.Forum

data class ForumCollection(
    val id : String,
    val idUser: String,
    val username: String,
    val photoProfile:String,
    var image : String? = null,
    var desc : String,
    var reply : MutableList<ReplyForum>? = null
)
