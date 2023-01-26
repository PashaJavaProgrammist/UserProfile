package com.user.profile.ui.screens

import android.widget.NumberPicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.user.profile.data.WeightUnit
import com.user.profile.ui.MainViewModel
import com.user.profile.ui.items.BottomBar

private const val MIN_KG = 20
private const val MAX_KG = 200
private const val MIN_LB = 45
private const val MAX_LB = 450

@Composable
fun WeightScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onNewWeight: (Int) -> Unit,
    onNewWeightUnit: (WeightUnit) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {

    val weight by viewModel.weight.collectAsState()
    val weightMinMax by remember(weight) {
        mutableStateOf(weightMinMax(weight.weightUnit))
    }

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
                    text = "Enter your weight",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSecondary,
                )
                Row {
                    NumberPicker(
                        modifier = Modifier,
                        value = weight.value,
                        minValue = weightMinMax.first,
                        maxValue = weightMinMax.second,
                        onValueChange = onNewWeight,
                    )
                    NumberPicker(
                        modifier = Modifier,
                        value = weight.weightUnit.ordinal,
                        minValue = 0,
                        maxValue = WeightUnit.values().lastIndex,
                        displayedValues = WeightUnit.values().map { it.name }.toTypedArray(),
                        onValueChange = {
                            onNewWeightUnit(WeightUnit.values()[it])
                        },
                    )
                }
            }
        },
        footer = {
            BottomBar(
                stepTitle = "1/3",
                onNextClick = onNextClick,
                onBackClick = onBackClick,
                nextButtonEnabled = weight.value != 0,
            )
        },
    )
}

private fun weightMinMax(weightUnit: WeightUnit): Pair<Int, Int> {
    return when (weightUnit) {
        WeightUnit.LB -> MIN_LB to MAX_LB
        WeightUnit.KG -> MIN_KG to MAX_KG
    }
}

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    value: Int,
    minValue: Int,
    maxValue: Int,
    displayedValues: Array<String> = emptyArray(),
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
                if (displayedValues.isNotEmpty()) {
                    this.displayedValues = displayedValues
                }
                setOnValueChangedListener { _, _, newVal ->
                    onValueChange(newVal)
                }
            }
        },
        update = { view: NumberPicker ->
            view.minValue = minValue
            view.maxValue = maxValue
            view.value = value
        }
    )
}
