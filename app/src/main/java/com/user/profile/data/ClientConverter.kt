package com.user.profile.data

import com.user.profile.ui.models.ClientUI
import java.text.SimpleDateFormat
import java.util.*

class ClientConverter {

    private val sdf = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    fun convert(client: Client): ClientUI {
        return ClientUI(
            id = client.id,
            weight = "${client.weight.value} ${client.weight.weightUnit.name}",
            dateOfBirth = sdf.format(Date(client.dateOfBirth)),
            imageUri = client.imageUri,
        )
    }
}
