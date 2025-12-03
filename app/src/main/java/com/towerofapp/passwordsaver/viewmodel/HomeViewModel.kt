package com.towerofapp.passwordsaver.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towerofapp.passwordsaver.data.AccountRepository
import com.towerofapp.passwordsaver.model.AccountEntity
import com.towerofapp.passwordsaver.util.CryptoManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeViewModel(private val repo: AccountRepository) : ViewModel() {
    private val KEY_ALIAS = "com.towerofapp.passwordsaver.masterkey"

    val accounts = repo.getAllAccounts()
        .map { list ->
            list.map { entity ->
                val decryptedPassword = try {
                    CryptoManager.decrypt(KEY_ALIAS, entity.password)
                } catch (e: Exception) {
                    "*****"
                }
                entity.copy(password = decryptedPassword)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList<AccountEntity>())

    fun deleteAccount(account: AccountEntity) {
        viewModelScope.launch {
              repo.delete(account)
        }
    }
}
