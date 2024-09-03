package com.exprnc.winditechnicaltask.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.exprnc.winditechnicaltask.data.datasource.local.room.entities.UserEntity

@Dao
interface UserDao {

    @Transaction
    suspend fun updateUser(user: UserEntity) {
        deleteAllUsers()
        insertUser(user)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getUser(): UserEntity?
}