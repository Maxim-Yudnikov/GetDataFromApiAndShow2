package com.maxim.getdatafromapiandshow2.data.cache

import com.maxim.getdatafromapiandshow2.data.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseCacheDataSource(
    private val dao: Dao,
    private val dataToRoomMapper: DataToRoomMapper
) : CacheDataSource {
    override suspend fun getAllItems(): List<DataItem> = withContext(Dispatchers.IO) {
        val roomList = dao.getAllFacts()
        return@withContext roomList.map { it.toDataItem() }
    }

    override suspend fun saveItem(fact: DataItem) = withContext(Dispatchers.IO) {
        dao.insertFact(dataToRoomMapper.map(fact))
    }
}