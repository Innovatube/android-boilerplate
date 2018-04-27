package com.kotlin.boilerplate.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Post : RealmObject() {
    var id: Int = 0
    var userId: Int = 0
    var title: String = ""
    var body: String = ""
}