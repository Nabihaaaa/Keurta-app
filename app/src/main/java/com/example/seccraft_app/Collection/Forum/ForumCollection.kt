package com.example.seccraft_app.Collection.Forum

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import java.time.LocalDateTime

data class ForumCollection(
    var id: String ="",
    var idUser: String ="",
    var image: String = "",
    var TextForum: String = "",
    var time: Any? = null,

)

