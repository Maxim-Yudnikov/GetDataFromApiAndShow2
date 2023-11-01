package com.maxim.getdatafromapiandshow2

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {
    @Test
    fun test_get_data() {
        val interactor = FakeInteractor()
        val communication = FakeCommunication()
        val mapper = BaseDataToUiMapper()
        val viewModel = MainViewModel(interactor, communication, mapper)

        viewModel.getFact()
        assertEquals(State.Success(text = "fact text"), communication.value)
    }


    private class FakeInteractor: Interactor {
        suspend fun getFact() : Item {
            return BaseItem("fact text")
        }
    }

    private class FakeCommunication: Communication {
        var value: State? = null

        override fun show(stete: State) {
            value = state
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<State>) {}
    }
}