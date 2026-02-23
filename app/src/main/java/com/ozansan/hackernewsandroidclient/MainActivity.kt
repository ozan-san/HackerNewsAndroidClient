package com.ozansan.hackernewsandroidclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.ozansan.hackernewsandroidclient.ui.theme.HackerNewsAndroidClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val database = Firebase.database("https://hacker-news.firebaseio.com")
        val topStoriesRef = database.getReference("v0/topstories")

        topStoriesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val topStoryIds = snapshot.children.map { it.getValue(Long::class.java) }
                // Now you have the IDs of the top stories
                // You can then fetch the details of each story
                println(topStoryIds)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                println("Failed to read value." + error.toException())
            }
        })

        setContent {
            HackerNewsAndroidClientTheme {
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
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HackerNewsAndroidClientTheme {
        Greeting("Android")
    }
}