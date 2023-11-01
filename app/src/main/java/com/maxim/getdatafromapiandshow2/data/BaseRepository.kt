package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.cache.CacheDataSource
import com.maxim.getdatafromapiandshow2.data.net.CloudDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseRepository(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource
): Repository {
    override suspend fun getItem(): DataItem = withContext(Dispatchers.IO) {
        return@withContext cloudDataSource.getFact()
    }

    override suspend fun getAllItems(): List<DataItem> = withContext(Dispatchers.IO) {
        return@withContext cacheDataSource.getAllItems()
    }

    override suspend fun saveItem(item: DataItem) = withContext(Dispatchers.IO) {
        cacheDataSource.saveItem(item)
    }
}