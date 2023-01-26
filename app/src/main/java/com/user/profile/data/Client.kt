package com.user.profile.data

data class Client(
    val id: Long,
    val weight: Weight,
    val dateOfBirth: Long,
    val imageUri: String,
)

data class Weight(
    val value: Int,
    val weightUnit: WeightUnit,
)

enum class WeightUnit {
    LB,
    KG,
}
