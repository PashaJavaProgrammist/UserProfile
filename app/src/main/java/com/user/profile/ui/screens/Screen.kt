package com.user.profile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.user.profile.R

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit,
    screenContent: @Composable ColumnScope.() -> Unit,
    footer: @Composable BoxScope.() -> Unit,
) {

    Box(
        modifier = modifier.fillMaxSize(),
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
            ) {
                Icon(
                    modifier = Modifier
                        .background(MaterialTheme.colors.secondary)
                        .height(48.dp)
                        .padding(12.dp)
                        .aspectRatio(1f)
                        .clickable(onClick = onBackClick),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    tint = MaterialTheme.colors.secondaryVariant,
                    contentDescription = "Back",
                )

                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.secondary),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        modifier = Modifier,
                        text = title,
                        style = MaterialTheme.typography.h2,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                }
            }

            screenContent()
        }

        Box(
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 8.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
        ) {
            Divider(
                modifier = Modifier.align(Alignment.TopCenter),
            )
            footer()
        }
    }
}
