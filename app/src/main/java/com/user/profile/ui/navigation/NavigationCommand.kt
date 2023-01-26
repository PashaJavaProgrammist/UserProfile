package com.user.profile.ui.navigation

sealed class NavigationCommand {
    data class Screen(val state: State) : NavigationCommand()
    object Back : NavigationCommand()
}
