package com.innovatube.boilerplate.danger

import java.io.IOException
import java.net.BindException
import java.nio.channels.AsynchronousCloseException

class Test {

    val i = Integer(1).toString() // temporary Integer instantiation just for the conversion

    fun forEachOnRangeTest() {
        (1..10).forEach {
            println(it)
        }
        (1 until 10).forEach {
            println(it)
        }
        (10 downTo 1).forEach {
            println(it)
        }
    }

    fun foo(strs: Array<String>) {
        bar(*strs)
    }

    fun bar(vararg strs: String) {
        strs.forEach { println(it) }
    }

    fun foo() {
        try {
            // ... do some I/O
        } catch(e: IOException) {
            if (e is AsynchronousCloseException || (e as BindException) != null) { }
        }
    }

    fun printStackTrace() {
        Thread.dumpStack()
    }

    fun bar() {
        try {
            // ...
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun rethrowCaughtException() {
        try {
            // ...
        } catch (e: IOException) {
            throw e
        }
        try {
            // ...
        } catch (e: IOException) {
            print(e.message)
            throw e
        }
    }

    fun foo(str: String?) {
        println(str!!.length)
    }



}