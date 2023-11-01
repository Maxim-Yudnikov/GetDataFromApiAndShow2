package com.maxim.getdatafromapiandshow2.domain

import com.maxim.getdatafromapiandshow2.data.DataItem
import com.maxim.getdatafromapiandshow2.data.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class BaseInteractorTest {
    @Test
    fun test_get_data_success() = runBlocking {
        val repository = FakeRepository()
        val interactor = BaseInteractor(repository)

        repository.returnSuccess = true
        val actual = interactor.getFact()
        val expected = DomainItem.BaseDomainItem("item")
        assertEquals(expected, actual)
    }

    @Test
    fun test_get_data_failed() = runBlocking {
        val repository = FakeRepository()
        val interactor = BaseInteractor(repository)

        repository.returnSuccess = false
        val actual = interactor.getFact()
        val expected = DomainItem.FailedDomainItem("error message")
        assertEquals(expected, actual)
    }


    private class FakeRepository : Repository {
        var returnSuccess = true
        override suspend fun getItem(): DataItem {
            return if (returnSuccess)
                DataItem.BaseDataItem("item")
            else
                DataItem.FailedDataItem("error message")
        }
    }
}