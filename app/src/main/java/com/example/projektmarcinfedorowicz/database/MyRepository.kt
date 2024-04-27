package com.example.projektmarcinfedorowicz.database

import android.content.Context
import kotlinx.coroutines.flow.Flow

class MyRepository(
    private val myDao: MyDao
) {
    fun getData(): Flow<List<DBItem>>? {
        return myDao?.getAllData()
    }

    fun addItem(item: DBItem) : Boolean {
        return myDao?.insert(item)!! >= 0
    }

    fun deleteItem(item: DBItem) : Boolean {
        return myDao?.delete(item)!! > 0
    }

    fun modifyItem(id: Int, productName: String, use: String, amount: Int, productType: Int, available: Boolean, bought: Boolean) {
        val modifiedItem = DBItem(productName, use, amount, productType, available)
        modifiedItem.id = id
        modifiedItem.Bought = bought
        myDao?.update(modifiedItem)
    }

    fun getItem(id: Int): DBItem? {
        return myDao?.getItem(id)
    }
}