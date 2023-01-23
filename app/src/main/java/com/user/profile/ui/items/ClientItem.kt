package com.user.profile.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.request.CachePolicy

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ClientItem(
    modifier: Modifier = Modifier,
    weight: String,
    dateOfBirth: String,
    imageUrl: String,
    onEditClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Image(
            modifier = Modifier
                .height(172.dp)
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
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier,
                text = "Weight",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSecondary,
            )
            Text(
                modifier = Modifier,
                text = weight,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier,
                text = "DOB",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSecondary,
            )
            Text(
                modifier = Modifier,
                text = dateOfBirth,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.clickable(onClick = onEditClick),
                text = "EDIT",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant,
            )
        }
    }
}
