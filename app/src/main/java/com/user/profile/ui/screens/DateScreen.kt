package com.user.profile.ui.screens

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
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
import java.time.LocalDate
import java.time.ZoneOffset

@Composable
fun DateScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onNewDate: (Long) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {

    val date by viewModel.dob.collectAsState()

    Screen(
        modifier = modifier,
        title = "Date of Birth",
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
                    text = "Enter your Date of Birth",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSecondary,
                )
                Calendar(
                    modifier = Modifier.wrapContentSize(),
                    time = date,
                    onDateSelected = { date ->
                        onNewDate(date)
                    },
                )
            }
        },
        footer = {
            BottomBar(
                stepTitle = "2/3",
                onNextClick = onNextClick,
                onBackClick = onBackClick,
                nextButtonEnabled = true,
            )
        },
    )
}

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    time: Long,
    onDateSelected: (Long) -> Unit,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            CalendarView(context).apply {
                maxDate = System.currentTimeMillis()
                date = time
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val date = LocalDate
                        .now()
                        .withMonth(month + 1)
                        .withYear(year)
                        .withDayOfMonth(dayOfMonth)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()
                        .toEpochMilli()
                    onDateSelected(date)
                }
            }
        },
        update = { view ->
            view.maxDate = System.currentTimeMillis()
            view.date = time
        }
    )
}
