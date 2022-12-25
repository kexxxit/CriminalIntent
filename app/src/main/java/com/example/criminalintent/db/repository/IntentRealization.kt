package com.example.criminalintent.db.repository

import androidx.lifecycle.LiveData
import com.example.criminalintent.ModelSave
import com.example.criminalintent.db.dao.IntentDao

class IntentRealization(private val intentDao: IntentDao): IntentRepository {


    override val allIntents: LiveData<List<ModelSave>>
        get() = intentDao.getAllIntents()

    override suspend fun insertIntent(modelSave: ModelSave, onSuccess: () -> Unit) {
        intentDao.insert(modelSave)
        onSuccess()
    }

}