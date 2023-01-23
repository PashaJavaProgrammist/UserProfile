package com.user.profile

sealed class State(val route: String) {
    object Clients : State("clients")
    object Weight : State("weight")
    object Date : State("date")
    object Photo : State("photo")
}
