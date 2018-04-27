package com.kotlin.boilerplate.model

import com.kotlin.boilerplate.data.Consts
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Comment : RealmObject() {
    var id: Int = 0
    var postId: Int = 0
    var name: String = ""
    var email: String = ""
    var body: String = ""
}