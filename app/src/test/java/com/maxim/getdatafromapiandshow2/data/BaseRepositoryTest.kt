package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.cache.BaseCachedItem
import com.maxim.getdatafromapiandshow2.data.cache.CacheDataSource
import com.maxim.getdatafromapiandshow2.data.domain.NoConnectionException
import com.maxim.getdatafromapiandshow2.data.domain.ServiceUnavailableException
import com.maxim.getdatafromapiandshow2.data.net.CloudDataSource
import com.maxim.getdatafromapiandshow2.domain.DomainItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class BaseRepositoryTest {
    @Test
    fun test_success() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = BaseCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        cloudDataSource.returnType = 0
        val actual = repository.getItem()
        val expected = DomainItem.BaseDomainItem("item")
        assertEquals(expected, actual)
    }

    @Test(expected = NoConnectionException::class)
    fun test_no_connection() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = BaseCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        cloudDataSource.returnType = 1
        val actual = repository.getItem()
    }

    @Test(expected = ServiceUnavailableException::class)
    fun test_service_unavailable() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = BaseCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        cloudDataSource.returnType = 2
        val actual = repository.getItem()
    }

    @Test
    fun test_save_item() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = BaseCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        val item = DataItem.BaseDataItem("data item")
        cachedItem.saveItem(item)
        repository.saveItem()

        assertEquals(1, cacheDataSource.savedFacts.count())
        assertEquals(DataItem.BaseDataItem("data item"), cacheDataSource.savedFacts[0])
    }

    @Test
    fun test_save_two_items_and_get_list() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = BaseCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        cachedItem.saveItem(DataItem.BaseDataItem("data item 1"))
        repository.saveItem()
        cachedItem.saveItem(DataItem.BaseDataItem("data item 2"))
        repository.saveItem()

        val actual = repository.getAllItems()
        val expected = listOf(
            DomainItem.BaseDomainItem("data item 1"),
            DomainItem.BaseDomainItem("data item 2")
        )

        assertEquals(2, cacheDataSource.savedFacts.count())
        assertEquals(expected, actual)
    }

    private class FakeCacheDataSource : CacheDataSource {
        var savedFacts = mutableListOf<DataItem>()
        override suspend fun getAllItems(): List<DataItem> {
            return savedFacts
        }

        override suspend fun saveItem(fact: DataItem) {
            savedFacts.add(fact)
        }

        override suspend fun removeItem(text: String) {
            TODO("Not yet implemented")
        }
    }



    private class FakeCloudDataSource : CloudDataSource {
        var returnType = 0
        override suspend fun getFact(): DataItem {
            when (returnType) {
                0 -> return DataItem.BaseDataItem("item")
                1 -> throw NoConnectionException()
                else -> throw ServiceUnavailableException()
            }
        }
    }
}