package com.bbb.thecatapi.ui.detail

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
fun DetailScreen(onClickExit: () -> Unit) {

    val viewModel = koinViewModel<DetailViewModel>()
    val list = viewModel.itemSelected.collectAsStateWithLifecycle()
    val listFavorites = viewModel.favorites.collectAsStateWithLifecycle()

    val item = list.value.firstOrNull()
    item?.let {
        val isFavorite = listFavorites.value.any { it.name == item.name }
        DetailScreenContent(
            item = it,
            isFavorite = isFavorite,
            onClickExit = onClickExit,
            onClickFavorite = {
                if (isFavorite) {
                    viewModel.removeFavorite(item)
                } else {
                    viewModel.addFavorite(item)
                }
            }
        )
    }
}

@Composable
private fun DetailScreenContent(
    item: BreedsModel,
    isFavorite: Boolean,
    onClickExit: () -> Unit,
    onClickFavorite: () -> Unit,
) {

    val colors = getColorTheme()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CustomTopBar(item = item, onClickExit = onClickExit)
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).padding(24.dp)) {

                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { onClickFavorite() },
                        modifier = Modifier.weight(0.1f)
                    ) {
                        if (isFavorite) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                tint = colors.primary,
                                contentDescription = ""
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                tint = colors.primary,
                                contentDescription = ""
                            )
                        }
                    }
                }

                Row {
                    Text(
                        text = "Origen: ",
                        maxLines = 1,
                        minLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colors.primary,
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = item.origen,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = colors.secondary,
                        style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Normal)
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Descripción",
                    maxLines = 1,
                    minLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.primary,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = item.description,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.secondary,
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Normal)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Puedes aprender más en:",
                    maxLines = 1,
                    minLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.primary,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = item.wikipediaUrl,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.secondary,
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Normal)
                )
            }
        }
    }
}

@Composable
private fun CustomTopBar(item: BreedsModel, onClickExit: () -> Unit) {

    val colors = getColorTheme()

    Column(modifier = Modifier.background(colors.backgroundMainColor).padding(top = 30.dp)) {

        Row(
            modifier = Modifier.padding(start = 20.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onClickExit() },
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    tint = colors.backgroundColor,
                    contentDescription = ""
                )
            }

            Text(
                text = "Detalle",
                style = TextStyle(
                    color = colors.primaryAccent,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = item.name,
                style = TextStyle(
                    color = colors.textSecondaryAccentColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }

        ImageItem(item)
    }
}

@Composable
private fun ImageItem(item: BreedsModel) {
    val colors = getColorTheme()

    Card(
        modifier = Modifier.fillMaxWidth().height(400.dp).padding(24.dp),
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
            }
        }
    }
}
