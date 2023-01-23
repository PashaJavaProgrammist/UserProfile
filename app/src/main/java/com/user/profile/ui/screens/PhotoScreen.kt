package com.user.profile.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.user.profile.ui.MainViewModel
import com.user.profile.ui.items.BottomBar

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onNewPhoto: (String) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {

    val imageUrl by viewModel.imageUrl.collectAsState()

    Screen(
        modifier = modifier,
        title = "Client Photo",
        onBackClick = onBackClick,
        screenContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (imageUrl.isNotEmpty()) {
                    Image(
                        modifier = Modifier
                            .height(256.dp)
                            .aspectRatio(0.8f)
                            .padding(8.dp),
                        painter = rememberImagePainter(
                            data = imageUrl,
                            imageLoader = LocalImageLoader.current,
                            builder = {
                                memoryCachePolicy(CachePolicy.ENABLED)
                            }
                        ),
                        contentScale = ContentScale.Inside,
                        contentDescription = "Client Image",
                    )
                }
                PhotoPicker(
                    modifier = Modifier.padding(16.dp),
                    onNewUri = {
                        onNewPhoto(it.toString())
                    }
                )
            }
        },
        footer = {
            BottomBar(
                stepTitle = "3/3",
                nextTitle = "DONE",
                onNextClick = onNextClick,
                onBackClick = onBackClick,
                nextButtonEnabled = imageUrl.isNotEmpty(),
            )
        },
    )
}

@Composable
fun PhotoPicker(
    modifier: Modifier = Modifier,
    onNewUri: (Uri) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { onNewUri(it) }
    }
    Text(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                launcher.launch("image/*")
            },
        text = "PICK PHOTO",
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.primaryVariant,
    )
}
