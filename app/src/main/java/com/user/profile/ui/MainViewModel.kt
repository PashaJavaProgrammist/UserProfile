package com.user.profile.ui

import androidx.lifecycle.ViewModel
import com.user.profile.data.Client
import com.user.profile.data.ClientConverter
import com.user.profile.ui.models.ClientUI
import com.user.profile.ui.utills.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class MainViewModel : ViewModel() {

    private companion object {
        const val NO_USER_ID = -1L
    }

    private val converter = ClientConverter() // todo: move to DI

    private val clientsList = mutableListOf<Client>()

    private var userForEditId = NO_USER_ID

    private val _weight = MutableStateFlow(0)
    val weight = _weight.asStateFlow()

    private val _dob = MutableStateFlow(System.currentTimeMillis())
    val dob = _dob.asStateFlow()

    private val _imageUrl = MutableStateFlow("")
    val imageUrl = _imageUrl.asStateFlow()

    private val _clients = MutableStateFlow(clientsList)
    val clients: Flow<List<ClientUI>> = _clients.map { it.map(converter::convert) }

    fun onAddClientClick() {
        clearData()
    }

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
        if (userForEditId == NO_USER_ID) {
            addNewUser()
        } else {
            editUser()
        }
        clearData()

        // to pass StateFlow '===' check
        _clients.value = clientsList.copy()
    }

    private fun addNewUser() {
        clientsList.add(
            Client(
                id = System.currentTimeMillis(),
                weight = weight.value,
                dateOfBirth = dob.value,
                imageUri = imageUrl.value,
            ),
        )
    }

    private fun editUser() {
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

    private fun clearData() {
        userForEditId = NO_USER_ID
        onNewWeight(0)
        onNewDate(System.currentTimeMillis())
        onNewPhoto("")
    }
}
