package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.net.CloudDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseRepository(
    private val cloudDataSource: CloudDataSource
): Repository {
    override suspend fun getItem(): DataItem = withContext(Dispatchers.IO) {
        return@withContext cloudDataSource.getFact()
    }
}