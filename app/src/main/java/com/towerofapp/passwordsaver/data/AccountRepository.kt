package com.towerofapp.passwordsaver.data

import com.towerofapp.passwordsaver.data.model.AccountEntity
import kotlinx.coroutines.flow.Flow


class AccountRepository(private val dao: AccountDao) {

    fun getAllAccounts(): Flow<List<AccountEntity>> = dao.getAllAccounts()
    suspend fun insert(account: AccountEntity) = dao.insert(account)
    suspend fun delete(account: AccountEntity) = dao.delete(account)
}