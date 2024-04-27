package com.example.projektmarcinfedorowicz.database

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.Flow

class MyViewModel(
    application: Application,
    myDB: MyDB?
): AndroidViewModel(application) {
    private var myRepository: MyRepository = MyRepository(myDB!!.myDao())

    fun getAllItems(): Flow<List<DBItem>>? = myRepository.getData()
    fun insertItem(item: DBItem) = myRepository.addItem(item)
    fun deleteItem(item: DBItem) = myRepository.deleteItem(item)
    fun getItem(id: Int): DBItem? = myRepository.getItem(id)
    fun modifyItem(id: Int, productName: String, use: String, amount: Int, productType: Int, available: Boolean, bought: Boolean) = myRepository.modifyItem(id, productName, use, amount, productType, available, bought)
}

class MyViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(
                application = application,
                myDB = MyDB.getDatabase(application)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}