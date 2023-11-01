package com.maxim.getdatafromapiandshow2.data

interface Repository {
    suspend fun getItem(): DataItem
    suspend fun getAllItems(): List<DataItem>
    suspend fun saveItem(item: DataItem)
}