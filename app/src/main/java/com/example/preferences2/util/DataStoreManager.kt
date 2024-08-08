package com.example.preferences2.util

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DataStoreManager(private val context:Context) {
    companion object {
        val Context.userDataStore: DataStore<MyUserInfo> by dataStore(
            fileName = "user.pb",
            serializer = DataStoreSerializer
        )
    }

    val storageData : Flow<String> = context.userDataStore.data.map {
        it.username
    }

    suspend fun saveUsername(username: String, context: Context) {
        try {
            withContext(Dispatchers.IO) {
                context.userDataStore.updateData { currentUser: MyUserInfo ->
                    currentUser.toBuilder()
                        .setUsername(username)
                        .build()
                }
            }
        } catch(e: Exception) {
            Log.e("Error", "Error writing to proto store: $e")
            throw e
        }
    }

    suspend fun clearPrefernces(){
        context.userDataStore.updateData {
            it.toBuilder().clear().build()
        }
    }

}