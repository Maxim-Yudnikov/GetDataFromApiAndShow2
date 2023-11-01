package com.maxim.getdatafromapiandshow2.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class BaseCommunication: Communication {
    private val liveData = MutableLiveData<State>()
    override fun show(state: State) {
        liveData.value = state
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<State>) {
        liveData.observe(owner, observer)
    }
}