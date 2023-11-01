package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.cache.CacheDataSource
import com.maxim.getdatafromapiandshow2.data.cache.CachedItem
import com.maxim.getdatafromapiandshow2.data.net.CloudDataSource
import com.maxim.getdatafromapiandshow2.domain.DomainItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseRepository(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource,
    private val cachedItem: CachedItem
) : Repository {

    override suspend fun getItem(): DomainItem = withContext(Dispatchers.IO) {
        try {
            val item = cloudDataSource.getFact()
            cachedItem.saveItem(item)
            return@withContext item.toDomainItem()
        } catch (e: Exception) {
            cachedItem.clear()
            throw e
        }

    }

    override suspend fun getAllItems(): List<DomainItem> = withContext(Dispatchers.IO) {
        return@withContext cacheDataSource.getAllItems().map { it.toDomainItem() }
    }

    override suspend fun saveItem() = withContext(Dispatchers.IO) {
        cacheDataSource.saveItem(cachedItem.getItem())
    }
}