package com.exprnc.winditechnicaltask.data.datasource.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exprnc.winditechnicaltask.data.datasource.local.room.dao.UserDao
import com.exprnc.winditechnicaltask.data.datasource.local.room.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}