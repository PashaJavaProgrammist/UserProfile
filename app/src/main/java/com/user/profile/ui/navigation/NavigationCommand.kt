package com.user.profile.ui.navigation

sealed class NavigationCommand {
    data class Screen(
        val state: State,
        val clearTop: Boolean = false,
    ) : NavigationCommand()

    object Back : NavigationCommand()
}
