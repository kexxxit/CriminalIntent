package com.example.criminalintent.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.criminalintent.ModelSave
import com.example.criminalintent.db.dao.IntentDao

@Database(entities = [ModelSave::class], version = 1)
abstract class IntentDataBase: RoomDatabase() {
    abstract fun getIntentDao(): IntentDao

    companion object{
        private var database: IntentDataBase ?= null

        @Synchronized
        fun getInstance(context: Context): IntentDataBase {
            return if (database == null) {
                database = Room.databaseBuilder(context, IntentDataBase::class.java, "db").build()
                database as IntentDataBase
            } else {
                database as IntentDataBase
            }
        }
    }
}