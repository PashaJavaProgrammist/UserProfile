package com.user.profile.di

import android.content.Context
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.user.profile.data.ClientConverter
import com.user.profile.ui.App
import com.user.profile.ui.MainViewModel

class DependencyContainer {

    companion object {
        val Context.dependencies: DependencyContainer
            get() = when (this) {
                is App -> dependencyContainer
                else -> applicationContext.dependencies
            }
    }

    val mainViewModelFactory by lazy {
        viewModelFactory {
            initializer {
                MainViewModel(
                    clientConverter = clientConverter,
                )
            }
        }
    }

    private val clientConverter by lazy { ClientConverter() }
}
