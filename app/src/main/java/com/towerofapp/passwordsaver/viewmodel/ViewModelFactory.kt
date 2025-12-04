package com.towerofapp.passwordsaver.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.towerofapp.passwordsaver.data.AccountRepository

class ViewModelFactory(private val repo: AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repo) as T
            modelClass.isAssignableFrom(AddAccountViewModel::class.java) -> AddAccountViewModel(repo) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
