package com.maxim.getdatafromapiandshow2.domain

import android.provider.ContactsContract.Data
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
    fun test_get_item() = runBlocking {
        val repository = FakeRepository()
        val interactor = BaseInteractor(repository, BaseFailureHandler())

        repository.returnType = 0
        var actual = interactor.getItem()
        var expected: DomainItem = DomainItem.BaseDomainItem("item")
        assertEquals(expected, actual)

        repository.returnType = 1
        actual = interactor.getItem()
        expected = DomainItem.FailedDomainItem("No connection")
        assertEquals(expected, actual)

        repository.returnType = 2
        actual = interactor.getItem()
        expected = DomainItem.FailedDomainItem("Service unavailable")
        assertEquals(expected, actual)
    }

    @Test
    fun test_get_items_list() = runBlocking {
        val repository = FakeRepository()
        val interactor = BaseInteractor(repository, BaseFailureHandler())

        repository.returnType = 0
        var actual = interactor.getItemsList()
        var expected: List<DomainItem> =
            listOf(DomainItem.BaseDomainItem("item 1"), DomainItem.BaseDomainItem("item 2"))
        assertEquals(expected, actual)

        repository.returnType = 1
        actual = interactor.getItemsList()
        expected = listOf(DomainItem.FailedDomainItem("No connection"))
        assertEquals(expected, actual)

        repository.returnType = 2
        actual = interactor.getItemsList()
        expected = listOf(DomainItem.FailedDomainItem("Service unavailable"))
        assertEquals(expected, actual)
    }

    @Test
    fun test_save_item() = runBlocking {
        val repository = FakeRepository()
        val interactor = BaseInteractor(repository, BaseFailureHandler())

        interactor.saveItem()
        assertEquals(1, repository.saveItemCounter)
    }

    @Test
    fun test_remove_item() = runBlocking {
        val repository = FakeRepository()
        val interactor = BaseInteractor(repository, BaseFailureHandler())

        interactor.removeItem("some item")
        assertEquals(1, repository.removeItemCounter)
        assertEquals("some item", repository.removeItemValue)
    }


    private class FakeRepository : Repository {
        var returnType = 0
        var saveItemCounter = 0
        var removeItemCounter = 0
        var removeItemValue = ""
        override suspend fun getItem(): DomainItem {
            when (returnType) {
                0 -> return DomainItem.BaseDomainItem("item")
                1 -> throw NoConnectionException()
                else -> throw ServiceUnavailableException()
            }
        }

        override suspend fun getAllItems(): List<DomainItem> {
            when (returnType) {
                0 -> return listOf(DomainItem.BaseDomainItem("item 1"), DomainItem.BaseDomainItem("item 2"))
                1 -> throw NoConnectionException()
                else -> throw ServiceUnavailableException()
            }
        }

        override suspend fun saveItem() {
            saveItemCounter++
        }

        override suspend fun removeItem(text: String) {
            removeItemCounter++
            removeItemValue = text
        }
    }
}