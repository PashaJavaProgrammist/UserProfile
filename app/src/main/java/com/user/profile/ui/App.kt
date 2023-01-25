package com.user.profile.ui

import android.app.Application
import com.user.profile.di.DependencyContainer

class App : Application() {

    val dependencyContainer = DependencyContainer()
}
