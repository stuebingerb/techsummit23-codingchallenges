package com.mediamarktsaturn.techsummit23

import org.apache.hc.core5.http.HttpResponse
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.Scanner

fun Serializable.toByteArray() = ByteArrayOutputStream().use { bos ->
    ObjectOutputStream(bos).use {
        it.writeObject(this)
        it.flush()
        bos.toByteArray()
    }
}

fun handleResponse(response: HttpResponse?): String = response?.run {
    when {
        code in 200..201 -> reasonPhrase
        code in 400..499 -> throw IllegalStateException("Server responses with client error")
        code in 500..599 -> throw IllegalStateException("Server responses with server error")
        else -> throw IllegalStateException("Receive unexpected response code " + code)
    }
} ?: throw IllegalStateException("Response is null")

private const val DELIMITER = "[^a-zA-Z'äöü]+"

fun File.countWord(): Map<String, Int> = runCatching {
    Scanner(this).use {
        it.useDelimiter(DELIMITER)
        val words = mutableListOf<String>()
        while (it.hasNext()) {
            words.add(it.next())
        }    
        words.groupingBy { it }.eachCount()
    }
}.getOrElse {
    throw IllegalArgumentException("Unable to read the current file ${this.name}!", it)
}
