package com.kotlin.boilerplate.model

import io.realm.RealmObject

open class NewComment : RealmObject() {
    var postId: Int = 0
    var name: String = ""
    var email: String = ""
    var body: String = ""
}