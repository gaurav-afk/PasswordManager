package com.towerofapp.passwordsaver.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String
)