package com.bbb.thecatapi.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.bbb.thecatapi.core.composableLibrary.LoadingScreen
import com.bbb.thecatapi.getColorTheme
import com.bbb.thecatapi.ui.core.navigation.bottomNavigationBar.BottomNavigationBar
import com.bbb.thecatapi.ui.core.navigation.bottomNavigationBar.BottomNavigationItem
import com.bbb.thecatapi.ui.core.navigation.bottomNavigationBar.NavigationBottom
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(onClickExit: () -> Unit, onNextScreen: () -> Unit) {

    val bottomNavigationItem = listOf(
        BottomNavigationItem.Online,
        BottomNavigationItem.Favorite
    )

    var currentRoute by remember { mutableStateOf(bottomNavigationItem[0].route) }
    val navController = rememberNavController()

    var showDarkBackgroundLoading by remember { mutableStateOf(false) }
    var showDarkBackground by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CustomTopBar(currentRoute, onClickExit = onClickExit)
            },
            bottomBar = {
                BottomNavigationBar(
                    bottomNavigationItem = bottomNavigationItem,
                    navController = navController,
                    currentScreen = { route ->
                        currentRoute = route
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {


                NavigationBottom(
                    navigator = navController,
                    showDarkBackgroundLoading = { isShow: Boolean ->
                        showDarkBackgroundLoading = isShow
                    },
                    showDarkBackground = { isShow: Boolean ->
                        showDarkBackground = isShow
                    },
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White.copy(alpha = 0f)
                                )
                            )
                        )
                        .align(Alignment.TopCenter)
                )
            }
        }
    }

    if (showDarkBackgroundLoading || showDarkBackground) {
        LoadingScreen(showLoading = showDarkBackground.not())
    }
}

@Composable
private fun CustomTopBar(currentRoute: String, onClickExit: () -> Unit) {

    val colors = getColorTheme()
    var instruction = "Online"

    if (currentRoute == BottomNavigationItem.Favorite.route) {
        instruction = "Favoritos"
    }

    Column(modifier = Modifier.background(colors.backgroundMainColor).padding(top = 30.dp)) {

        Row(modifier = Modifier.padding(start = 20.dp, top = 8.dp)) {
            Text(
                text = "Catálogo",
                style = TextStyle(
                    color = colors.textSecondaryAccentColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = instruction,
                style = TextStyle(
                    color = colors.textSecondaryAccentColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }
        PersonalCard(currentRoute = currentRoute, onClickExit = onClickExit)
    }
}

@Composable
private fun PersonalCard(currentRoute: String, onClickExit: () -> Unit) {

    val colors = getColorTheme()
    var rotated by remember { mutableStateOf(false) }
    val rotate by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(250)
    )

    Card(
        modifier = Modifier.padding(start = 45.dp, end = 45.dp, top = 8.dp, bottom = 8.dp)
            .height(180.dp)
            .background(colors.backgroundMainColor)
            .graphicsLayer {
                rotationY = rotate
                cameraDistance = 8 * density
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        rotated = currentRoute == BottomNavigationItem.Favorite.route
        if (rotated) {
            BackCard(onClickExit)
        } else {
            FrondCard(onClickExit)
        }
    }
}

@Composable
private fun FrondCard(onClickExit: () -> Unit) {

    Box {

        Image(//image background
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = ""
        )

        Column {
            HeaderFrondCard { onClickExit() }
            Instructions(
                title = "Revisa nuestros gatitos",
                subTitle = "Puedes marcar como favoritos a todos los que te gusten"
            )
        }
    }
}

@Composable
private fun HeaderFrondCard(onClickExit: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(color = Color.Transparent)
    ) {

        Image(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "",
        )

        Row(modifier = Modifier.padding(top = 8.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onClickExit() }) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
private fun BackCard(onClickExit: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize().graphicsLayer { rotationY = 180f }) {

        Image(//image background
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = ""
        )

        Column {
            HeaderBackCard { onClickExit() }
            Instructions(
                title = "Revisa tus gatitos favoritos",
                subTitle = "Disfruta de los gatitos que has decidido marcar como favoritos"
            )
        }
    }
}

@Composable
private fun HeaderBackCard(onClickExit: () -> Unit) {

    val colors = getColorTheme()

    Row(modifier = Modifier.fillMaxWidth().height(70.dp)) {
        Column(modifier = Modifier.padding(top = 12.dp, start = 8.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Total de gatitos guardados",
                    color = colors.textColor,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Image(
                    imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                    contentDescription = ""
                )
            }
            Text(
                text = "14",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onClickExit() }) {
            Image(
                modifier = Modifier.padding(top = 14.dp),
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun Instructions(
    title: String,
    subTitle: String,
    colorText: Color = getColorTheme().textColor
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = title, color = colorText,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = subTitle,
                color = colorText,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}