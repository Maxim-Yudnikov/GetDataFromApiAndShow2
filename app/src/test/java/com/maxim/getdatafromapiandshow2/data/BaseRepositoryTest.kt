package com.maxim.getdatafromapiandshow2.data

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
        val repository = BaseRepository(cloudDataSource)

        cloudDataSource.returnType = 0
        val actual = repository.getItem()
        val expected = DataItem.BaseDataItem("item")
        assertEquals(expected, actual)
    }

    @Test(expected = NoConnectionException::class)
    fun test_no_connection() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val repository = BaseRepository(cloudDataSource)

        cloudDataSource.returnType = 1
        val actual = repository.getItem()
    }

    @Test(expected = ServiceUnavailableException::class)
    fun test_service_unavailable() = runBlocking {
        val cloudDataSource = FakeCloudDataSource()
        val repository = BaseRepository(cloudDataSource)

        cloudDataSource.returnType = 2
        val actual = repository.getItem()
    }


    private class FakeCloudDataSource: CloudDataSource {
        var returnType = 0
        override suspend fun getFact(): DataItem {
            when(returnType) {
                0 -> return DataItem.BaseDataItem("item")
                1 -> throw NoConnectionException()
                else -> throw ServiceUnavailableException()
            }
        }
    }
}