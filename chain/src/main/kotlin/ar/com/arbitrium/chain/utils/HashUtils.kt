package ar.com.arbitrium.chain.utils

import java.math.BigInteger
import java.security.MessageDigest

    enum class Algorithm(val code: String) {
        SHA1("SHA-1"), SHA256("SHA-256"), SHA512("SHA-512")
    }

    fun String.hash(algorithm: Algorithm): String {
        val messageDigest = MessageDigest.getInstance(algorithm.code)
        messageDigest.update(this.toByteArray())
        return String.format("%064x", BigInteger(1, messageDigest.digest()))
    }
