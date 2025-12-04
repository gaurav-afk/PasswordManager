package com.towerofapp.passwordsaver.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towerofapp.passwordsaver.data.AccountRepository
import com.towerofapp.passwordsaver.data.model.AccountEntity
import com.towerofapp.passwordsaver.util.CryptoManager
import kotlinx.coroutines.launch


class AddAccountViewModel(private val repo: AccountRepository) : ViewModel() {
    private val KEY_ALIAS = "com.towerofapp.passwordsaver.masterkey"

    fun addAccount(name: String, email: String, password: String) {
        viewModelScope.launch {
            val encryptedPassword = CryptoManager.encrypt(KEY_ALIAS, password)
            repo.insert(
                AccountEntity(
                    name = name,
                    email = email,
                    password = encryptedPassword
                )
            )
        }
    }

    fun updateAccount(account: AccountEntity) {
        viewModelScope.launch {
            val encryptedPassword = CryptoManager.encrypt(KEY_ALIAS, account.password)
            repo.insert(account.copy(password = encryptedPassword))
        }
    }
}
