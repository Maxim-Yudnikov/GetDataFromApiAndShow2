package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.cache.CacheDataSource
import com.maxim.getdatafromapiandshow2.data.domain.NoConnectionException
import com.maxim.getdatafromapiandshow2.data.domain.ServiceUnavailableException
import com.maxim.getdatafromapiandshow2.data.net.CloudDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class BaseRepositoryTest {
    @Test
    fun test_success() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val repository = BaseRepository(cloudDataSource, cacheDataSource)

        cloudDataSource.returnType = 0
        val actual = repository.getItem()
        val expected = DataItem.BaseDataItem("item")
        assertEquals(expected, actual)
    }

    @Test(expected = NoConnectionException::class)
    fun test_no_connection() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val repository = BaseRepository(cloudDataSource, cacheDataSource)

        cloudDataSource.returnType = 1
        val actual = repository.getItem()
    }

    @Test(expected = ServiceUnavailableException::class)
    fun test_service_unavailable() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val repository = BaseRepository(cloudDataSource, cacheDataSource)

        cloudDataSource.returnType = 2
        val actual = repository.getItem()
    }

    @Test
    fun test_save_item() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val repository = BaseRepository(cloudDataSource, cacheDataSource)

        val item = DataItem.BaseDataItem("data item")
        repository.saveItem(item)

        assertEquals(1, cacheDataSource.savedFacts.count())
        assertEquals(item, cacheDataSource.savedFacts[0])
    }

    @Test
    fun test_save_two_items_and_get_list() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val repository = BaseRepository(cloudDataSource, cacheDataSource)

        repository.saveItem(DataItem.BaseDataItem("data item 1"))
        repository.saveItem(DataItem.BaseDataItem("data item 2"))

        val actual = repository.getAllItems()
        val expected = listOf(
            DataItem.BaseDataItem("data item 1"),
            DataItem.BaseDataItem("data item 2")
        )

        assertEquals(2, cacheDataSource.savedFacts.count())
        assertEquals(expected, expected)
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

    private class FakeCacheDataSource : CacheDataSource {
        var savedFacts = mutableListOf<DataItem>()
        override suspend fun getAllItems(): List<DataItem> {
            return savedFacts
        }

        override suspend fun saveItem(fact: DataItem) {
            savedFacts.add(fact)
        }
    }
}