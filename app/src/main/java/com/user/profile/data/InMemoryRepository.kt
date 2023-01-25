package com.user.profile.data

class InMemoryRepository : Repository {

    private val clientsList = mutableListOf<Client>()

    override val clients: List<Client>
        get() = clientsList

    override fun clientByIdOrNull(clientId: Long): Client? {
        return clientsList.firstOrNull { it.id == clientId }
    }

    override fun addClient(client: Client) {
        clientsList.add(client)
    }

    override fun updateClient(clientId: Long, newWeight: Int, newData: Long, newImageUri: String) {
        clientByIdOrNull(clientId)?.let { client ->
            val index = clientsList.indexOf(client)
            clientsList.removeAt(index = index)
            clientsList.add(
                index = index,
                element = Client(
                    id = client.id,
                    weight = newWeight,
                    dateOfBirth = newData,
                    imageUri = newImageUri,
                ),
            )
        }
    }
}
