package com.example.criminalintent.db.repository

import androidx.lifecycle.LiveData
import com.example.criminalintent.ModelSave

interface IntentRepository {
    val allIntents: LiveData<List<ModelSave>>
    suspend fun insertIntent(modelSave: ModelSave, onSuccess:() -> Unit)
}