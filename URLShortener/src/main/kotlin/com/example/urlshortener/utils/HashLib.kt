package com.example.urlshortener.utils

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter




internal object HashLib {

    fun generateHash(data : String) : String {
        val md = MessageDigest.getInstance("MD5")
        md.update(data.toByteArray())
        val digest = md.digest()
        return DatatypeConverter.printHexBinary(digest)
    }

}