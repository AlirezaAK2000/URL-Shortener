package com.example.urlshortener.utils

import java.nio.charset.Charset
import java.util.*

internal object RandomString {
    fun getAlphaNumericString(n: Int = 6): String {
        val array = ByteArray(256)
        Random().nextBytes(array)
        val randomString = String(array, Charset.forName("UTF-8"))
        val r = StringBuffer()
        val alphaNumericString = randomString
            .replace("[^A-Za-z0-9]".toRegex(), "")
        var temp = n
        for (k in alphaNumericString.indices) {
            if (Character.isLetter(alphaNumericString[k])
                && temp > 0
                || Character.isDigit(alphaNumericString[k])
                && temp > 0
            ) {
                r.append(alphaNumericString[k])
                temp--
            }
        }
        return r.toString()
    }

}