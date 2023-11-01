package com.maxim.getdatafromapiandshow2.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface Communication {
    fun show(state: State)
    fun observe(owner: LifecycleOwner, observer: Observer<State>)
}