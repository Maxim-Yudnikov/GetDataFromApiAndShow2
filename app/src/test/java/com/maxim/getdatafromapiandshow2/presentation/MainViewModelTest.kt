package com.maxim.getdatafromapiandshow2.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.maxim.getdatafromapiandshow2.domain.DomainItem
import com.maxim.getdatafromapiandshow2.domain.Interactor
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {
    @Test
    fun test_get_data() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)

        interactor.returnSuccess = true
        viewModel.getItem()
        assertEquals(State.Success(text = "fact text"), communication.value)
    }

    @Test
    fun test_get_error_data() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)

        interactor.returnSuccess = false
        viewModel.getItem()
        assertEquals(State.Failed(text = "error text"), communication.value)
    }

    @Test
    fun test_save_item() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)

        viewModel.saveItem()
        assertEquals(1, interactor.saveFactCount)
    }


    private class FakeInteractor : Interactor {
        var returnSuccess = true
        var saveFactCount = 0
        override suspend fun getFact(): DomainItem {
            return if (returnSuccess)
                DomainItem.BaseDomainItem("fact text")
            else
                DomainItem.FailedDomainItem("error text")
        }

        override suspend fun getAllFacts(): List<DomainItem> {
            TODO("Not yet implemented")
        }

        override suspend fun saveFact() {
            saveFactCount++
        }

        override suspend fun removeItem(text: String) {
            TODO("Not yet implemented")
        }
    }

    private class FakeCommunication : Communication {
        var value: State? = null

        override fun show(state: State) {
            value = state
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<State>) {}


        override fun getList(): List<UiItem> {
            TODO("Not yet implemented")
        }

        override fun showList(list: List<UiItem>) {
            TODO("Not yet implemented")
        }

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
            TODO("Not yet implemented")
        }

        override fun getDiffResult(): DiffUtil.DiffResult {
            TODO("Not yet implemented")
        }
    }
}