package com.towerofapp.passwordsaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.towerofapp.passwordsaver.data.AccountRepository
import com.towerofapp.passwordsaver.data.AppDatabase
import com.towerofapp.passwordsaver.ui.screens.HomeScreen
import com.towerofapp.passwordsaver.viewmodel.HomeViewModel
import com.towerofapp.passwordsaver.viewmodel.AddAccountViewModel
import com.towerofapp.passwordsaver.viewmodel.ViewModelFactory
import kotlin.jvm.java


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "password_db"
        ).build()

        val repo = AccountRepository(db.accountDao())
        val factory = ViewModelFactory(repo)

        setContent {
            val homeVM: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
            val addVM: AddAccountViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)

            HomeScreen(homeVM, addVM)
        }
    }
}

