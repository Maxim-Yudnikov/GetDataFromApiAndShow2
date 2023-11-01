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

        viewModel.getFact()
        assertEquals(State.Success(text = "fact text"), communication.value)
    }


    private class FakeInteractor: Interactor {
        override suspend fun getFact() : DomainItem {
            return DomainItem.BaseDomainItem("fact text")
        }
    }

    private class FakeCommunication: Communication {
        var value: State? = null

        override fun show(state: State) {
            value = state
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<State>) {}
    }
}