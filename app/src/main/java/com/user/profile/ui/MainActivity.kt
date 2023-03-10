package com.user.profile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.user.profile.di.DependencyContainer.Companion.dependencies
import com.user.profile.ui.navigation.NavigationCommand
import com.user.profile.ui.navigation.State
import com.user.profile.ui.screens.ClientsScreen
import com.user.profile.ui.screens.DateScreen
import com.user.profile.ui.screens.PhotoScreen
import com.user.profile.ui.screens.WeightScreen
import com.user.profile.ui.theme.UserProfileTheme
import com.user.profile.ui.utills.hideSystemUI


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemUI()

        val viewModel: MainViewModel by viewModels { dependencies.mainViewModelFactory }

        setContent {
            UserProfileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                    content = {
                        Navigation(viewModel = viewModel)
                    },
                )
            }
        }
    }

    @Composable
    private fun Navigation(viewModel: MainViewModel) {
        val navController = rememberNavController()

        NavigationGraph(
            navController = navController,
            viewModel = viewModel,
        )

        Navigator(
            viewModel = viewModel,
            navController = navController,
        )

        BackPressCallback(navController = navController)
    }

    @Composable
    private fun NavigationGraph(
        navController: NavHostController,
        viewModel: MainViewModel,
    ) {
        NavHost(
            navController = navController,
            startDestination = State.Clients.route,
        ) {
            composable(State.Clients.route) {
                ClientsScreen(
                    viewModel = viewModel,
                    onBackClick = onBackPressedDispatcher::onBackPressed,
                    onEditClientClick = viewModel::onEditUserClick,
                    onAddClient = viewModel::onAddClientClick,
                )
            }
            composable(State.Weight.route) {
                WeightScreen(
                    viewModel = viewModel,
                    onBackClick = viewModel::onBack,
                    onNewWeight = viewModel::onNewWeight,
                    onNewWeightUnit = viewModel::onNewWeightUnit,
                    onNextClick = viewModel::openDate,
                )
            }

            composable(State.Date.route) {
                DateScreen(
                    viewModel = viewModel,
                    onBackClick = viewModel::onBack,
                    onNewDate = viewModel::onNewDate,
                    onNextClick = viewModel::openPhoto,
                )
            }

            composable(State.Photo.route) {
                PhotoScreen(
                    viewModel = viewModel,
                    onBackClick = viewModel::onBack,
                    onNewPhoto = viewModel::onNewPhoto,
                    onNextClick = viewModel::completeFlow,
                )
            }
        }
    }

    @Composable
    private fun Navigator(
        viewModel: MainViewModel,
        navController: NavHostController,
    ) {
        LaunchedEffect(this) {
            viewModel.navigation
                .flowWithLifecycle(lifecycle)
                .collect { command ->
                    when (command) {
                        NavigationCommand.Back -> navController.popBackStack()
                        is NavigationCommand.Screen -> navController.navigate(route = command.state.route)
                    }
                }
        }
    }

    @Composable
    private fun BackPressCallback(navController: NavHostController) {
        onBackPressedDispatcher.addCallback(owner = this) {
            if (navController.currentDestination?.route == State.Clients.route) {
                finish()
            } else {
                handleOnBackPressed()
            }
        }
    }
}
