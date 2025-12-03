package com.towerofapp.passwordsaver.data

import androidx.room.*
import com.towerofapp.passwordsaver.model.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {


    @Query("SELECT * FROM accounts ORDER BY id DESC")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountEntity)

    @Delete
    suspend fun delete(account: AccountEntity)

    @Update
    suspend fun update(account: AccountEntity)
}