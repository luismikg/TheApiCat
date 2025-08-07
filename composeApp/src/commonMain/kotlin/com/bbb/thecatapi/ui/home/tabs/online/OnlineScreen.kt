package com.bbb.thecatapi.ui.home.tabs.online

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.getColorTheme
import com.bbb.thecatapi.isAndroid
import com.bbb.thecatapi.isDesktop
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.blackCat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnlineScreen(
    showDarkBackgroundLoading: (Boolean) -> Unit,
    showDarkBackground: (Boolean) -> Unit
) {
    val viewModel = koinViewModel<OnlineViewModel>()
    val state by viewModel.state.collectAsState()
    val list = state.breedsModel.collectAsLazyPagingItems()

    var isRefreshing by remember { mutableStateOf(false) }
    var userScrollEnabled by remember { mutableStateOf(true) }

    PullRefreshList(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            userScrollEnabled = isRefreshing.not()
            showDarkBackground(true)
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                viewModel.refresh()
                isRefreshing = false
                userScrollEnabled = isRefreshing.not()
                showDarkBackground(false)
            }
        }
    ) { lazyGridState ->
        BreedsGrid(
            list = list,
            showDarkBackgroundLoading = showDarkBackgroundLoading,
            lazyGridState = lazyGridState,
            userScrollEnabled = userScrollEnabled
        )
    }
}

@Composable
private fun BreedsGrid(
    list: LazyPagingItems<BreedsModel>,
    showDarkBackgroundLoading: (Boolean) -> Unit,
    lazyGridState: LazyGridState,
    userScrollEnabled: Boolean
) {
    val columns = if (isAndroid()) {
        GridCells.Fixed(count = 1)
    } else {
        GridCells.Adaptive(300.dp)
    }

    LazyVerticalGrid(
        state = lazyGridState,
        userScrollEnabled = userScrollEnabled,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        columns = columns,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        showDarkBackgroundLoading(false)
        when {
            list.loadState.refresh is LoadState.Loading && list.itemCount == 0 -> {
                //initial loading
                showDarkBackgroundLoading(true)
            }

            list.loadState.refresh is LoadState.NotLoading && list.itemCount == 0 -> {
                item {
                    Text("No items")
                }
            }

            else -> {
                if (isAndroid()) {
                    item {
                        Spacer(modifier = Modifier.size(28.dp))
                    }
                }

                items(list.itemCount) { position ->
                    list[position]?.let { breedsModel ->
                        ImageItem(item = breedsModel, onClickItem = {})
                    }
                }

                if (list.loadState.append is LoadState.Loading) {
                    //Loading more items
                    showDarkBackgroundLoading(true)
                }
            }
        }
    }
}

@Composable
private fun ImageItem(item: BreedsModel, onClickItem: (BreedsModel) -> Unit) {

    val colors = getColorTheme()
    Card(
        modifier = Modifier.fillMaxWidth().height(400.dp),
        shape = RoundedCornerShape(percent = 12)
    ) {
        Box(contentAlignment = Alignment.BottomStart) {
            Box(Modifier.fillMaxSize().background(colors.backgroundColor.copy(alpha = 0.5f)))

            if (item.image.url.isEmpty()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Res.drawable.blackCat),
                    alpha = 0.08f,
                    contentScale = ContentScale.Fit,
                    contentDescription = "",
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(item.image.url)
                        .crossfade(true)
                        .listener(
                            onError = { request, result ->
                                println("Error loading image: ${result.throwable}")
                            },
                            onSuccess = { request, result ->
                                println("Loaded image successfully")
                            }
                        )
                        .build(),
                    placeholder = painterResource(Res.drawable.blackCat),
                    error = painterResource(Res.drawable.blackCat),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.9f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
            )

            Row(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.name,
                        maxLines = 1,
                        minLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colors.primary,
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = item.temperament,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = colors.backgroundColor,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal)
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier.weight(0.1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = colors.backgroundColor,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
fun PullRefreshList(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
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