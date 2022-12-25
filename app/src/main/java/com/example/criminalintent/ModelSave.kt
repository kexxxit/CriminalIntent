package com.example.criminalintent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Модель для сохранения в память


@Entity(tableName = "intent_table")
data class ModelSave(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val date: String,

    @ColumnInfo
    val imageId: ByteArray,

    @ColumnInfo
    val isSolved: Boolean
)
