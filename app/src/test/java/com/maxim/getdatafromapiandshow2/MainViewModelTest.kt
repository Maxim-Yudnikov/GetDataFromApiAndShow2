package com.maxim.getdatafromapiandshow2

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {
    @Test
    fun test_get_data() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)

        interactor.returnSuccess = true
        viewModel.getFact()
        assertEquals(State.Success(text = "fact text"), communication.value)
    }

    @Test
    fun test_get_error_data() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)

        interactor.returnSuccess = false
        viewModel.getFact()
        assertEquals(State.Failed(text = "error text"), communication.value)
    }


    private class FakeInteractor : Interactor {
        var returnSuccess = true
        override suspend fun getFact(): DomainItem {
            return if (returnSuccess)
                DomainItem.BaseDomainItem("fact text")
            else
                DomainItem.FailedDomainitem("error text")
        }
    }

    private class FakeCommunication : Communication {
        var value: State? = null

        override fun show(state: State) {
            value = state
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<State>) {}
    }
}