package com.user.profile.ui.navigation

sealed class State(val route: String) {
    object Clients : State("clients")
    object Weight : State("weight")
    object Date : State("date")
    object Photo : State("photo")
}
