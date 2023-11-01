package com.maxim.getdatafromapiandshow2.data.cache

import com.maxim.getdatafromapiandshow2.data.DataItem
import com.maxim.getdatafromapiandshow2.data.domain.NoCachedDataException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseCacheDataSource(
    private val dao: Dao
) : CacheDataSource {
    override suspend fun getAllItems(): List<DataItem> = withContext(Dispatchers.IO) {
        val roomList = dao.getAllFacts()
        if(roomList.isNotEmpty())
            return@withContext roomList.map { it.toDataItem() }
        else
            throw NoCachedDataException()
    }

    override suspend fun saveItem(fact: DataItem) = withContext(Dispatchers.IO) {
        dao.insertFact(fact.toRoomItem())
    }

    override suspend fun removeItem(text: String) {
        dao.removeFact(text)
    }
}