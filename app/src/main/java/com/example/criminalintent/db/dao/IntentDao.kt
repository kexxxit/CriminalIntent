package com.example.criminalintent.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.criminalintent.ModelSave

@Dao
interface IntentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(modelSave: ModelSave)

    @Query("SELECT * from intent_table")
    fun getAllIntents(): LiveData<List<ModelSave>>
}