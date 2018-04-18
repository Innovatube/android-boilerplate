package com.kotlin.boilerplate.model

import io.realm.RealmObject


open class Post : RealmObject() {
    var userId: Int = 0
    var id: Int = 0
    var title: String = ""
    var body: String = ""
}