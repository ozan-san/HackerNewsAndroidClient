package com.ozansan.hackernewsandroidclient.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ozansan.hackernewsandroidclient.ui.theme.HackerNewsAndroidClientTheme

@Composable
fun TabSelector(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var rowWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val selectedIndex = options.indexOf(selectedOption)

    val indicatorOffset: Dp by animateDpAsState(
        targetValue = (rowWidth / options.size) * selectedIndex,
        animationSpec = tween(),
        label = "indicatorOffset"
    )

    val selectedColor = MaterialTheme.colorScheme.background
    val unselectedColor = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .width(225.dp)
            .height(35.dp)
            .border(1.dp, unselectedColor)
            .onSizeChanged {
                rowWidth = with(density) { it.width.toDp() }
            }
    ) {
        Box(
            modifier = Modifier
                .width(rowWidth / options.size)
                .fillMaxHeight()
                .offset(x = indicatorOffset)
                .background(unselectedColor)
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, option ->
                val itemModifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onOptionSelected(option) }

                val textColor by animateColorAsState(
                    targetValue = if (selectedIndex == index) selectedColor else unselectedColor,
                    label = "textColor"
                )

                Box(
                    modifier = if (index > 0) {
                        itemModifier.drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            drawLine(
                                color = unselectedColor,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = strokeWidth
                            )
                        }
                    } else {
                        itemModifier
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        color = textColor,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "Light Mode",
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TabSelectorPreview() {
    var selectedTab by remember { mutableStateOf("TOP") }
    val tabs = listOf("NEW", "TOP", "BEST")

    HackerNewsAndroidClientTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TabSelector(
                options = tabs,
                selectedOption = selectedTab,
                onOptionSelected = { selectedTab = it }
            )
        }
    }
}