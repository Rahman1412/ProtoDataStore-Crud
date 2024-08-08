package com.example.preferences2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.example.preferences2.ui.theme.Preferences2Theme
import com.example.preferences2.util.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Preferences2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val ins = DataStoreManager(context)
    var username by remember {
        mutableStateOf("")
    }
    LaunchedEffect(true) {
        ins.storageData.collect{
            username = it
        }
    }
    Column {
        Text(
            text = "Hello $username!",
            modifier = modifier
        )

        Button(onClick = {
            runBlocking {
                withContext(Dispatchers.IO){
                    val data =  username + 1
                    ins.saveUsername(data,context)
                }
            }
        }) {
            Text(text = "Save Data")
        }
        
        Button(onClick = {
            runBlocking {
                ins.clearPrefernces()
            }
        }) {
            Text(text = "Clear Data")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Preferences2Theme {
        Greeting("Android")
    }
}