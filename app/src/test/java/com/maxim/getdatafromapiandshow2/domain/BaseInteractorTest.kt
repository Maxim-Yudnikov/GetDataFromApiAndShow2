package com.maxim.getdatafromapiandshow2.domain

import com.maxim.getdatafromapiandshow2.data.DataItem
import com.maxim.getdatafromapiandshow2.data.Repository
import com.maxim.getdatafromapiandshow2.data.domain.BaseFailure
import com.maxim.getdatafromapiandshow2.data.domain.NoConnectionError
import com.maxim.getdatafromapiandshow2.data.domain.NoConnectionException
import com.maxim.getdatafromapiandshow2.data.domain.ServiceUnavailableError
import com.maxim.getdatafromapiandshow2.data.domain.ServiceUnavailableException
import com.maxim.getdatafromapiandshow2.data.domain.UnknownError
import com.maxim.getdatafromapiandshow2.data.net.BaseFailureHandler
import com.maxim.getdatafromapiandshow2.data.net.FailureHandler
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.NotSerializableException

class BaseInteractorTest {
    @Test
    fun test_get_data_success() = runBlocking {
        val repository = FakeRepository()
        val failureHandler = BaseFailureHandler()
        val interactor = BaseInteractor(repository, failureHandler)

        repository.returnType = 0
        val actual = interactor.getFact()
        val expected = DomainItem.BaseDomainItem("item")
        assertEquals(expected, actual)
    }

    @Test
    fun test_get_data_failed() = runBlocking {
        val repository = FakeRepository()
        val failureHandler = BaseFailureHandler()
        val interactor = BaseInteractor(repository, failureHandler)

        repository.returnType = 1
        var actual = interactor.getFact()
        var expected = DomainItem.FailedDomainItem("No connection")
        assertEquals(expected, actual)

        repository.returnType = 2
        actual = interactor.getFact()
        expected = DomainItem.FailedDomainItem("Service unavailable")
        assertEquals(expected, actual)
    }

    private class FakeRepository : Repository {
        var returnType = 0
        override suspend fun getItem(): DataItem {
            when(returnType) {
                0 -> return DataItem.BaseDataItem("item")
                1 -> throw NoConnectionException()
                else -> throw ServiceUnavailableException()
            }
        }

        override suspend fun getAllItems(): List<DataItem> {
            TODO("Not yet implemented")
        }

        override suspend fun saveItem(item: DataItem) {
            TODO("Not yet implemented")
        }
    }
}