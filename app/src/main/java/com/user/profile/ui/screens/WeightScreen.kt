package com.user.profile.ui.screens

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.user.profile.ui.MainViewModel
import com.user.profile.ui.items.BottomBar

@Composable
fun WeightScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onNewWeight: (Int) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {

    val weight by viewModel.weight.collectAsState()

    // todo: Add lb support
    Screen(
        modifier = modifier,
        title = "Body Weight",
        onBackClick = onBackClick,
        screenContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Enter your weight, kg",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSecondary,
                )
                NumberPicker(
                    modifier = Modifier,
                    value = weight,
                    minValue = 20, // todo to const
                    maxValue = 200,
                    onValueChange = { newWeight ->
                        onNewWeight(newWeight)
                    },
                )
            }
        },
        footer = {
            BottomBar(
                stepTitle = "1/3",
                onNextClick = onNextClick,
                onBackClick = onBackClick,
                nextButtonEnabled = weight != 0,
            )
        },
    )
}

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    value: Int,
    minValue: Int,
    maxValue: Int,
    onValueChange: (Int) -> Unit,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            NumberPicker(context).apply {
                this.minValue = minValue
                this.maxValue = maxValue
                wrapSelectorWheel = false
                this.value = value
            }
        },
        update = { view: NumberPicker ->
            view.setOnValueChangedListener { _, _, newVal ->
                onValueChange(newVal)
            }
        }
    )
}
