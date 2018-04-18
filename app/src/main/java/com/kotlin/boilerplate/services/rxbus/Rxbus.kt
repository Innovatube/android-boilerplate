package com.kotlin.boilerplate.services.rxbus

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class Rxbus private constructor() {
    private val relay: PublishRelay<Any> = PublishRelay.create()

    fun post(event: Any) {
        relay.accept(event)
    }

    fun toObservable(): Observable<Any> {
        return relay.subscribeOn(Schedulers.io())
    }

    private object Holder {
        val INSTANCE = Rxbus()
    }

    companion object {
        val instance: Rxbus by lazy { Holder.INSTANCE }
    }
}
