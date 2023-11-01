package com.maxim.getdatafromapiandshow2.data.cache

import com.maxim.getdatafromapiandshow2.data.DataItem

interface CacheDataSource {
    suspend fun getAllItems(): List<DataItem>
    suspend fun saveItem(fact: DataItem)
    suspend fun removeItem(text: String)
}