package com.user.profile.data

interface Repository {

    val clients: List<Client>

    fun clientByIdOrNull(clientId: Long): Client?
    fun addClient(client: Client)
    fun updateClient(clientId: Long, newWeight: Weight, newData: Long, newImageUri: String)
}
