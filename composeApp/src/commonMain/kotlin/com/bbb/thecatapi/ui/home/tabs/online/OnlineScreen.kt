package com.bbb.thecatapi.ui.home.tabs.online

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bbb.thecatapi.core.composableLibrary.LoadingScreen
import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.getColorTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnlineScreen(
    showDarkBackground: (Boolean) -> Unit
) {
    val viewModel = koinViewModel<OnlineViewModel>()
    val state by viewModel.state.collectAsState()
    val list = state.breedsModel ?: emptyList()
    showDarkBackground(state.isLoading)


    if (list.isEmpty() && state.isLoading.not()) {
        /*Column(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.no_loans),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                color = colors.textColor
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                painter = painterResource(Res.drawable.empty_state_icon),
                contentDescription = ""
            )
        }*/
    } else {
        val listState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.padding(top = 30.dp, start = 4.dp, end = 4.dp),
            state = listState
        ) {
            item { Spacer(modifier = Modifier.size(20.dp)) }
            items(list) { item ->
                ImageItem(item = item) { /*onClickItem(item)*/ }
                Spacer(modifier = Modifier.size(20.dp))
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

            SubcomposeAsyncImage(
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
                loading = { LoadingScreen() },
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

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
