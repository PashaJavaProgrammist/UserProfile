package com.user.profile.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun BoxScope.BottomBar(
    stepTitle: String,
    nextTitle: String = "NEXT",
    nextButtonEnabled: Boolean,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.CenterStart)
    ) {

        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(onClick = onBackClick),
            text = "BACK",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stepTitle,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable(onClick = onNextClick, enabled = nextButtonEnabled),
            text = nextTitle,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant,
            textAlign = TextAlign.Center,
        )
    }
}
