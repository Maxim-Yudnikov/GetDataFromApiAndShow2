package com.maxim.getdatafromapiandshow2.data

import android.provider.ContactsContract.Data
import com.maxim.getdatafromapiandshow2.data.cache.BaseCachedItem
import com.maxim.getdatafromapiandshow2.data.cache.CacheDataSource
import com.maxim.getdatafromapiandshow2.data.cache.CachedItem
import com.maxim.getdatafromapiandshow2.data.domain.NoConnectionException
import com.maxim.getdatafromapiandshow2.data.domain.ServiceUnavailableException
import com.maxim.getdatafromapiandshow2.data.net.CloudDataSource
import com.maxim.getdatafromapiandshow2.domain.DomainItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class BaseRepositoryTest {

    @Test
    fun test_get_item_success() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = FakeCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        cloudDataSource.returnType = 0
        val actual = repository.getItem()
        val expected = DomainItem.BaseDomainItem("item from cloud")
        assertEquals(DataItem.BaseDataItem("item from cloud"), cachedItem.cache)
        assertEquals(actual, expected)
    }

    @Test(expected = NoConnectionException::class)
    fun test_get_item_no_connection() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = FakeCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        cloudDataSource.returnType = 1
        val actual = repository.getItem()
    }

    @Test(expected = ServiceUnavailableException::class)
    fun test_get_item_service_unavailable() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = FakeCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        cloudDataSource.returnType = 2
        val actual = repository.getItem()
    }

    @Test
    fun test_get_all_items() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = FakeCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        val actual = repository.getAllItems()
        val expected =
            listOf(DomainItem.BaseDomainItem("item 1"), DomainItem.BaseDomainItem("item 2"))
        assertEquals(expected, actual)
    }

    @Test
    fun test_save_item() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = FakeCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        cachedItem.cache = DataItem.BaseDataItem("cached item")
        repository.saveItem()
        assertEquals(1, cacheDataSource.saveItemCounter)
        assertEquals(DataItem.BaseDataItem("cached item"), cacheDataSource.saveItemValue)
    }

    @Test
    fun test_remove_item() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val cachedItem = FakeCachedItem()
        val repository = BaseRepository(cloudDataSource, cacheDataSource, cachedItem)

        repository.removeItem("some item")
        assertEquals(1, cacheDataSource.removeItemCounter)
        assertEquals("some item", cacheDataSource.removeItemValue)
    }


    private class FakeCacheDataSource : CacheDataSource {
        var saveItemCounter = 0
        var saveItemValue: DataItem? = null
        var removeItemCounter = 0
        var removeItemValue = ""
        override suspend fun getAllItems(): List<DataItem> {
            return listOf(DataItem.BaseDataItem("item 1"), DataItem.BaseDataItem("item 2"))
        }

        override suspend fun saveItem(fact: DataItem) {
            saveItemCounter++
            saveItemValue = fact
        }

        override suspend fun removeItem(text: String) {
            removeItemCounter++
            removeItemValue = text
        }
    }

    private class FakeCloudDataSource : CloudDataSource {
        var returnType = 0
        override suspend fun getFact(): DataItem {
            when (returnType) {
                0 -> return DataItem.BaseDataItem("item from cloud")
                1 -> throw NoConnectionException()
                else -> throw ServiceUnavailableException()
            }
        }
    }

    private class FakeCachedItem() : CachedItem {
        var cache: DataItem? = null
        override fun saveItem(dataItem: DataItem) {
            cache = dataItem
        }

        override fun getItem(): DataItem {
            return cache!!
        }

        override fun clear() {
            cache = null
        }
    }
}