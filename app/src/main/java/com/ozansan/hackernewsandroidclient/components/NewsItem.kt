package com.ozansan.hackernewsandroidclient.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ozansan.hackernewsandroidclient.landing.newslist.UINewsState
import com.ozansan.hackernewsandroidclient.ui.theme.HackerNewsAndroidClientTheme

@Composable
fun NewsItem(uiNews: UINewsState) {
    var contentHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val unselectedColor = MaterialTheme.colorScheme.onBackground

    Row(
        modifier = Modifier
            .border(1.dp, unselectedColor)
            .padding(16.dp)
            .height(contentHeight+32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .defaultMinSize(minWidth = 56.dp)
                .padding(start = 4.dp, end = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = uiNews.score?.toString() ?: "0",
                style = MaterialTheme.typography.labelLarge,
            )
            Text(text = "PTS", fontSize = 12.sp)
        }
        Spacer(
            modifier = Modifier
                .height(contentHeight)
                .width(1.dp)
                .background(unselectedColor)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.onSizeChanged {
                contentHeight = with(density) { it.height.toDp() }
            }
        ) {
            Text(
                text = uiNews.title ?: "No title",
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "USR: ${uiNews.by}",
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "TIME: ${formatTimeAgo(uiNews.time ?: 0)}",
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "COM: ${uiNews.descendants ?: 0}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

private fun formatTimeAgo(time: Long): String {
    val now = System.currentTimeMillis() / 1000
    val diff = now - time
    return when {
        diff < 0 -> "in the future"
        diff < 60 -> "${diff}s ago"
        diff < 3600 -> "${diff / 60}m ago"
        diff < 86400 -> "${diff / 3600}h ago"
        else -> "${diff / 86400}d ago"
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun NewsItemPreview() {
    HackerNewsAndroidClientTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NewsItem(
                uiNews = UINewsState(
                    id = 1,
                    title = "QUANTUM COMPUTING BREAKTHROUGH: SILICON-BASED QUBITS MAINTAIN COHERENCE FOR 10 MINUTES.",
                    score = 982,
                    by = "admin_x",
                    time = System.currentTimeMillis() / 1000 - 7200, // 2 hours ago
                    descendants = 142
                )
            )
        }
    }
}
