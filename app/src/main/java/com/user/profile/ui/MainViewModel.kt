package com.user.profile.ui

import androidx.lifecycle.ViewModel
import com.user.profile.data.Client
import com.user.profile.data.ClientConverter
import com.user.profile.ui.models.ClientUI
import com.user.profile.ui.utills.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class MainViewModel : ViewModel() {

    private val converter = ClientConverter() // todo: move to DI

    private val clientsList = mutableListOf<Client>()

    private var userForEditId = -1L

    private val _weight = MutableStateFlow(0)
    val weight: StateFlow<Int> = _weight

    private val _dob = MutableStateFlow(System.currentTimeMillis())
    val dob: StateFlow<Long> = _dob

    private val _imageUrl = MutableStateFlow("")
    val imageUrl: StateFlow<String> = _imageUrl

    private val _clients = MutableStateFlow(clientsList)
    val clients: Flow<List<ClientUI>> = _clients.map { it.map(converter::convert) }

    fun onEditUserClick(clientId: Long) {
        userForEditId = clientId
        clientsList.firstOrNull { it.id == clientId }?.let {
            onNewWeight(it.weight)
            onNewDate(it.dateOfBirth)
            onNewPhoto(it.imageUri)
        }
    }

    fun onNewWeight(newWeight: Int) {
        _weight.value = newWeight
    }

    fun onNewDate(newDate: Long) {
        _dob.value = newDate
    }

    fun onNewPhoto(newPhotoUri: String) {
        _imageUrl.value = newPhotoUri
    }

    fun completeFlow() {
        if (userForEditId == -1L) {
            // add new user
            clientsList.add(
                Client(
                    id = System.currentTimeMillis(),
                    weight = weight.value,
                    dateOfBirth = dob.value,
                    imageUri = imageUrl.value,
                ),
            )
        } else {
            // edit old user
            clientsList.firstOrNull { it.id == userForEditId }?.let { client ->
                val index = clientsList.indexOf(client)
                clientsList.removeAt(index = index)
                clientsList.add(
                    index = index,
                    element = Client(
                        id = client.id,
                        weight = weight.value,
                        dateOfBirth = dob.value,
                        imageUri = imageUrl.value,
                    ),
                )
            }
        }
        clearData()

        // to pass StateFlow '===' check
        _clients.value = clientsList.copy()
    }

    private fun clearData() {
        onNewWeight(0)
        onNewDate(System.currentTimeMillis())
        onNewPhoto("")
    }
}
