package com.example.preferences2.util

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

object DataStoreSerializer : Serializer<MyUserInfo> {
    override val defaultValue: MyUserInfo = MyUserInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MyUserInfo {
        return MyUserInfo.parseFrom(input)
    }

    override suspend fun writeTo(t: MyUserInfo, output: OutputStream) {
        t.writeTo(output)
    }
}