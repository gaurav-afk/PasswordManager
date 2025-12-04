package com.towerofapp.passwordsaver.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.towerofapp.passwordsaver.data.model.AccountEntity


@Database(entities = [AccountEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}