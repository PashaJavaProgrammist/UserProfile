package com.user.profile.ui

import androidx.lifecycle.ViewModel
import com.user.profile.ui.navigation.NavigationCommand
import com.user.profile.ui.navigation.State
import com.user.profile.data.Client
import com.user.profile.data.ClientConverter
import com.user.profile.ui.models.ClientUI
import com.user.profile.ui.utills.copy
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {

    private companion object {
        const val NO_USER_ID = -1L
    }

    private val converter = ClientConverter() // todo: move to DI

    private val clientsList = mutableListOf<Client>()

    private var userForEditId = NO_USER_ID

    private val _navigation = Channel<NavigationCommand>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val navigation = _navigation.receiveAsFlow()

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
        openWeight()
    }

    fun onEditUserClick(clientId: Long) {
        userForEditId = clientId
        clientsList.firstOrNull { it.id == clientId }?.let {
            onNewWeight(it.weight)
            onNewDate(it.dateOfBirth)
            onNewPhoto(it.imageUri)
        }
        openWeight()
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
        openClients()
    }

    fun onBack() {
        _navigation.trySend(NavigationCommand.Back)
    }

    fun openDate() {
        _navigation.trySend(NavigationCommand.Screen(State.Date))
    }

    fun openPhoto() {
        _navigation.trySend(NavigationCommand.Screen(State.Photo))
    }

    private fun openWeight() {
        _navigation.trySend(NavigationCommand.Screen(State.Weight))
    }

    private fun openClients() {
        _navigation.trySend(NavigationCommand.Screen(State.Clients))
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
