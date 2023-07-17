package com.mediamarktsaturn.techsummit23

import org.apache.hc.core5.http.HttpResponse
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.ObjectOutputStream
import java.io.Serializable

fun Serializable.toByteArray() = ByteArrayOutputStream().use { bos ->
    ObjectOutputStream(bos).use {
        it.writeObject(this)
        it.flush()
        bos.toByteArray()
    }
}

fun handleResponse(response: HttpResponse?) = response?.run {
    when {
        code in 200..201 -> reasonPhrase
        code in 400..499 -> error("Server responses with client error")
        code in 500..599 -> error("Server responses with server error")
        else -> error("Receive unexpected response code $code")
    }
} ?: error("Response is null")

private const val DELIMITER = "[^a-zA-Z'äöü]+"

fun File.countWord() = runCatching {
    readText().split(Regex(DELIMITER)).filterNot { it.isBlank() }.groupingBy { it }.eachCount()
}.getOrElse {
    throw IllegalArgumentException("Unable to read the current file $name!", it)
}
