package com.zspirytus.zspermission.utils

@Suppress("unused")
object ArrayUtils {

    fun concat(a: Array<String>, b: Array<String>): Array<String?> {
        val c = arrayOfNulls<String>(a.size + b.size)
        System.arraycopy(a, 0, c, 0, a.size)
        System.arraycopy(b, 0, c, a.size, c.size - a.size)
        return c
    }
}
