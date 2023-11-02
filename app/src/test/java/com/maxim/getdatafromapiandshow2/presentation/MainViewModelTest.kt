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
    fun test_get_item() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)

        viewModel.getItem()
        assertEquals(State.Success("text"), communication.state)
    }

    @Test
    fun test_save_item() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)

        viewModel.saveItem()
        assertEquals(1, interactor.saveFactCounter)
        val expected = listOf(
            UiItem.BaseUiItem("item 1"), UiItem.BaseUiItem("item 2"),
            UiItem.BaseUiItem("item 3"), UiItem.BaseUiItem("item 4")
        )
        for(i in expected.indices) {
            assertEquals(true, expected[i].same(communication.itemList!![i]))
        }
    }

    @Test
    fun test_get_item_list() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)

        viewModel.getItemList()
        val expected = listOf(
            UiItem.BaseUiItem("item 1"), UiItem.BaseUiItem("item 2"),
            UiItem.BaseUiItem("item 3"), UiItem.BaseUiItem("item 4")
        )
        for(i in expected.indices) {
            assertEquals(true, expected[i].same(communication.itemList!![i]))
        }
    }

    @Test
    fun test_remove_item() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)

        viewModel.removeItem("test item")
        assertEquals(1, interactor.removeItemCounter)
        assertEquals("test item", interactor.removeItemValue)
        val expected = listOf(
            UiItem.BaseUiItem("item 1"), UiItem.BaseUiItem("item 2"),
            UiItem.BaseUiItem("item 3"), UiItem.BaseUiItem("item 4")
        )
        for(i in expected.indices) {
            assertEquals(true, expected[i].same(communication.itemList!![i]))
        }
    }


    private class FakeInteractor : Interactor {
        var returnSuccess = true
        var saveFactCounter = 0
        var removeItemCounter = 0
        var removeItemValue = ""
        override suspend fun getItem(): DomainItem {
            return if (returnSuccess)
                DomainItem.BaseDomainItem("text")
            else
                DomainItem.FailedDomainItem("error")
        }

        override suspend fun getItemsList(): List<DomainItem> {
            return listOf(
                DomainItem.BaseDomainItem("item 1"), DomainItem.BaseDomainItem("item 2"),
                DomainItem.BaseDomainItem("item 3"), DomainItem.BaseDomainItem("item 4")
            )
        }

        override suspend fun saveItem() {
            saveFactCounter++
        }

        override suspend fun removeItem(text: String) {
            removeItemCounter++
            removeItemValue = text
        }
    }

    private class FakeCommunication : Communication {
        var state: State? = null
        var itemList: List<UiItem>? = null
        override fun show(state: State) {
            this.state = state
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<State>) {
            TODO("Not yet implemented")
        }

        override fun getList(): List<UiItem> {
            TODO("Not yet implemented")
        }

        override fun showList(list: List<UiItem>) {
            this.itemList = list
        }

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
            TODO("Not yet implemented")
        }

        override fun getDiffResult(): DiffUtil.DiffResult {
            TODO("Not yet implemented")
        }
    }
}