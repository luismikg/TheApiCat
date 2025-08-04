package com.bbb.thecatapi.ui.home.tabs.online

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.blackCat
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnlineScreen(
    showDarkBackground: (Boolean) -> Unit
) {
    val viewModel = koinViewModel<OnlineViewModel>()
    val state by viewModel.state.collectAsState()
    val list = state.breedsModel.collectAsLazyPagingItems()

    BreedsGrid(list = list, showDarkBackground = showDarkBackground)
}

@Composable
private fun BreedsGrid(list: LazyPagingItems<BreedsModel>, showDarkBackground: (Boolean) -> Unit) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        columns = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        showDarkBackground(false)
        when {
            list.loadState.refresh is LoadState.Loading && list.itemCount == 0 -> {
                //initial loading
                showDarkBackground(true)
            }

            list.loadState.refresh is LoadState.NotLoading && list.itemCount == 0 -> {
                item {
                    Text("No items")
                }
            }

            else -> {
                item {
                    Spacer(modifier = Modifier.size(28.dp))
                }
                items(list.itemCount) { position ->
                    list[position]?.let { breedsModel ->
                        ImageItem(item = breedsModel, onClickItem = {})
                    }
                }

                if (list.loadState.append is LoadState.Loading) {
                    //Loading more items
                    showDarkBackground(true)
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
