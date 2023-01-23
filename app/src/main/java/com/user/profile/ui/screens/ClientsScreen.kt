package com.user.profile.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.user.profile.ui.MainViewModel
import com.user.profile.ui.items.ClientItem
import com.user.profile.ui.models.ClientUI

@Composable
fun ClientsScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onBackClick: () -> Unit,
    onEditClientClick: (Long) -> Unit,
    onAddClient: () -> Unit,
) {

    Screen(
        modifier = modifier,
        title = "Clients",
        onBackClick = onBackClick,
        screenContent = {
            val clients by viewModel.clients.collectAsState(initial = emptyList())

            if (clients.isEmpty()) {
                Text(
                    modifier = Modifier.padding(24.dp),
                    text = "No clients added so far.",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSecondary,
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 56.dp),
                    ) {
                        items(clients) { client: ClientUI ->
                            ClientItem(
                                weight = client.weight,
                                dateOfBirth = client.dateOfBirth,
                                imageUrl = client.imageUri,
                                onEditClick = { onEditClientClick(client.id) },
                            )
                        }
                    }
                }
            }
        },
        footer = {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(onClick = onAddClient),
                text = "ADD CLIENT",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant,
                textAlign = TextAlign.Center,
            )
        },
    )
}
