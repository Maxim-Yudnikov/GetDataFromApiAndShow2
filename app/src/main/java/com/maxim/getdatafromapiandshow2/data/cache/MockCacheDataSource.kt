package com.maxim.getdatafromapiandshow2.data.cache

import com.maxim.getdatafromapiandshow2.data.DataItem
import com.maxim.getdatafromapiandshow2.data.domain.NoCachedDataException
import com.maxim.getdatafromapiandshow2.domain.DomainItem

class MockCacheDataSource : CacheDataSource {
    private val list = mutableListOf<DataItem>()
    override suspend fun getAllItems(): List<DataItem> {
        return if (list.isEmpty())
            throw NoCachedDataException()
        else
            list.reversed()
    }

    override suspend fun saveItem(fact: DataItem) {
        list.add(fact)
    }

    override suspend fun removeItem(text: String) {
        val index = list.indexOf(DataItem.BaseDataItem(text))
        list.removeAt(index)
    }

}