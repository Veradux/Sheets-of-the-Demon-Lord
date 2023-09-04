package com.veradux.sheetsofthedemonlord.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * The top bar will collapse and expand anytime the list is scrolled up or down,
 * regardless if the user is at the top of the screen.
 */
@Composable
fun ScreenWithScrollableTopBar(
    topBarHeight: Dp,
    topBar: @Composable (BoxScope.(IntOffset) -> Unit),
    content: @Composable (BoxScope.(PaddingValues) -> Unit)
) {
    val searchBarHeightPx = with(LocalDensity.current) { topBarHeight.roundToPx().toFloat() }
    val searchBarOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val newOffset = searchBarOffsetHeightPx.value + available.y
                searchBarOffsetHeightPx.value = newOffset.coerceIn(-searchBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        content(PaddingValues(top = topBarHeight))
        topBar(IntOffset(x = 0, y = searchBarOffsetHeightPx.value.roundToInt()))
    }
}

@Preview
@Composable
fun TestPreview() {
    ScreenWithScrollableTopBar(
        topBarHeight = 64.dp,
        topBar = {
            Text(
                text = "Top Bar",
                modifier = Modifier
                    .offset { it }
                    .background(color = MaterialTheme.colorScheme.surface)
            )
        },
    ) {
        LazyColumn(contentPadding = it) {
            items(50) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
