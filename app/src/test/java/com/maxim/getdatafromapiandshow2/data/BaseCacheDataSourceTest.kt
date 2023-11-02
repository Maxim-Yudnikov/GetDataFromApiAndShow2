package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.cache.BaseCacheDataSource
import com.maxim.getdatafromapiandshow2.data.cache.Dao
import com.maxim.getdatafromapiandshow2.data.cache.FactRoomModel
import com.maxim.getdatafromapiandshow2.data.domain.NoCachedDataException
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals

class BaseCacheDataSourceTest {
    @Test
    fun test_get_all_items_success() = runBlocking {
        val dao = FakeDao()
        val cacheDataSource = BaseCacheDataSource(dao)

        dao.returnEmpty = false
        val actual = cacheDataSource.getAllItems()
        val expected = listOf(DataItem.BaseDataItem("item 1"), DataItem.BaseDataItem("item 2"))
        assertEquals(expected, actual)
    }

    @Test(expected = NoCachedDataException::class)
    fun test_get_all_items_error() = runBlocking {
        val dao = FakeDao()
        val cacheDataSource = BaseCacheDataSource(dao)

        dao.returnEmpty = true
        val actual = cacheDataSource.getAllItems()
    }

    @Test
    fun test_save_item() = runBlocking {
        val dao = FakeDao()
        val cacheDataSource = BaseCacheDataSource(dao)

        cacheDataSource.saveItem(DataItem.BaseDataItem("item"))
        assertEquals(1, dao.saveItemCounter)
        assertEquals(DataItem.BaseDataItem("item"), dao.saveItemValue)
    }

    @Test
    fun test_remove_item() = runBlocking {
        val dao = FakeDao()
        val cacheDataSource = BaseCacheDataSource(dao)

        cacheDataSource.removeItem("some item")
        assertEquals(1, dao.removeItemCounter)
        assertEquals("some item", dao.removeItemValue)
    }


    private class FakeDao : Dao {
        var returnEmpty = false
        var saveItemCounter = 0
        var saveItemValue: DataItem? = null
        var removeItemCounter = 0
        var removeItemValue = ""
        override suspend fun insertFact(fact: FactRoomModel) {
            saveItemCounter++
            saveItemValue = fact.toDataItem()
        }

        override suspend fun getAllFacts(): List<FactRoomModel> {
            return if (returnEmpty)
                emptyList()
            else
                listOf(FactRoomModel(0, "item 1", 123), FactRoomModel(0, "item 2", 123))
        }

        override suspend fun removeFact(text: String) {
            removeItemCounter++
            removeItemValue = text
        }
    }
}