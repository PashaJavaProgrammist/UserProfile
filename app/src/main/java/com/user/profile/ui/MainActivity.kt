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
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.user.profile.State
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
        val viewModel: MainViewModel by viewModels()
        hideSystemUI()
        setContent {
            UserProfileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                    content = {
                        // todo: extract all navController.navigate into Navigator.kt and navigate from VM
                        val navController = rememberNavController()

                        BackPressCallback(navController)

                        NavHost(
                            navController = navController,
                            startDestination = State.Clients.route,
                        ) {
                            composable(State.Clients.route) {
                                ClientsScreen(
                                    viewModel = viewModel,
                                    onBackClick = { finish() },
                                    onEditClientClick = { clientId ->
                                        viewModel.onEditUserClick(clientId)
                                        navController.navigate(State.Weight.route)
                                    },
                                    onAddClient = { navController.navigate(State.Weight.route) },
                                )
                            }
                            composable(State.Weight.route) {
                                WeightScreen(
                                    viewModel = viewModel,
                                    onBackClick = navController::popBackStack,
                                    onNewWeight = viewModel::onNewWeight,
                                    onNextClick = { navController.navigate(State.Date.route) },
                                )
                            }

                            composable(State.Date.route) {
                                DateScreen(
                                    viewModel = viewModel,
                                    onBackClick = navController::popBackStack,
                                    onNewDate = viewModel::onNewDate,
                                    onNextClick = { navController.navigate(State.Photo.route) },
                                )
                            }

                            composable(State.Photo.route) {
                                PhotoScreen(
                                    viewModel = viewModel,
                                    onBackClick = navController::popBackStack,
                                    onNewPhoto = viewModel::onNewPhoto,
                                    onNextClick = {
                                        viewModel.completeFlow()
                                        navController.navigate(State.Clients.route)
                                    },
                                )
                            }
                        }
                    },
                )
            }
        }
    }

    @Composable
    private fun BackPressCallback(navController: NavHostController) {
        onBackPressedDispatcher.addCallback {
            if (navController.currentDestination?.route == State.Clients.route) {
                finish()
            } else {
                handleOnBackPressed()
            }
            remove()
        }
    }
}
