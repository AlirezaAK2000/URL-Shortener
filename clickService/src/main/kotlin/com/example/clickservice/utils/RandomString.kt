package com.example.clickservice.utils

import java.nio.charset.Charset
import java.util.*

// using Regular Expressions method


internal object RandomString {
    fun getAlphaNumericString(n: Int = 6): String {

        // length is bounded by 256 Character
        val array = ByteArray(256)
        Random().nextBytes(array)
        val randomString = String(array, Charset.forName("UTF-8"))

        // Create a StringBuffer to store the result
        val r = StringBuffer()

        // remove all spacial char
        val AlphaNumericString = randomString
            .replace("[^A-Za-z0-9]".toRegex(), "")

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        var temp = n
        for (k in 0 until AlphaNumericString.length) {
            if (Character.isLetter(AlphaNumericString[k])
                && temp > 0
                || Character.isDigit(AlphaNumericString[k])
                && temp > 0
            ) {
                r.append(AlphaNumericString[k])
                temp--
            }
        }

        // return the resultant string
        return r.toString()
    }

}