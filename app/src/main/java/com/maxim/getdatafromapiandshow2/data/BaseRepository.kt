package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.cache.CacheDataSource
import com.maxim.getdatafromapiandshow2.data.net.CloudDataSource
import com.maxim.getdatafromapiandshow2.domain.DomainItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseRepository(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource,
): Repository {
    override suspend fun getItem(): DomainItem = withContext(Dispatchers.IO) {
        return@withContext cloudDataSource.getFact().toDomainItem()
    }

    override suspend fun getAllItems(): List<DomainItem> = withContext(Dispatchers.IO) {
        return@withContext cacheDataSource.getAllItems().map { it.toDomainItem() }
    }

    override suspend fun saveItem(item: DomainItem) = withContext(Dispatchers.IO) {
        cacheDataSource.saveItem(item.mapToData())
    }
}