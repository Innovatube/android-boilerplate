package com.innovatube.boilerplate.utils

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object TestUtils {

    @Throws(Exception::class)
    fun getStringFromPath(obj: Any, fileName: String): String {
        val classLoader = obj.javaClass.classLoader
        val resource = classLoader.getResource(fileName)

        val fileReader = FileReader(File(resource.path))
        val builder = StringBuilder()

        BufferedReader(fileReader).use { reader ->
            var string: String? = reader.readLine()
            while (string != null) {
                builder.append(string + System.getProperty("line.separator"))
                string = reader.readLine()
            }
        }

        return builder.toString()
    }

}
