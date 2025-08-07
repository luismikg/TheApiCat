package com.bbb.thecatapi.core.composableLibrary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.bbb.thecatapi.isDesktop
import kotlinx.coroutines.launch

@Composable
fun PullRefreshList(
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable (LazyGridState) -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    val isAtTop by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex == 0 &&
                    lazyGridState.firstVisibleItemScrollOffset == 0
        }
    }

    var overscrollOffset by remember { mutableStateOf(0f) }
    val threshold = 120f

    // Pull-to-refresh for Android (using NestedScroll)
    val nestedScrollConnection = remember(isAtTop, isRefreshing) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (isAtTop && available.y > 0) {
                    overscrollOffset += available.y
                    return Offset(0f, available.y)
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (overscrollOffset > threshold && isAtTop && !isRefreshing) {
                    onRefresh()
                }
                overscrollOffset = 0f
                return Velocity.Zero
            }
        }
    }

    // Pull-to-refresh for Desktop using the mouse
    val pointerInputModifier = if (isDesktop()) {
        Modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    if (overscrollOffset > threshold && isAtTop && !isRefreshing) {
                        onRefresh()
                    }
                    overscrollOffset = 0f
                }
            ) { change, dragAmount ->
                change.consume()
                coroutineScope.launch {
                    if (isAtTop && dragAmount.y > 0) {
                        overscrollOffset += dragAmount.y
                    } else {
                        lazyGridState.scrollBy(-dragAmount.y)
                    }
                }
            }
        }
    } else {
        Modifier
    }

    Column(
        modifier = modifier
            .nestedScroll(nestedScrollConnection)
            .then(pointerInputModifier)
    ) {
        AnimatedVisibility(visible = overscrollOffset > 10 || isRefreshing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        content(lazyGridState)
    }
}