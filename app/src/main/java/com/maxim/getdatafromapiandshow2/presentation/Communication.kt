package com.maxim.getdatafromapiandshow2.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult

interface Communication: ListCommunication {
    fun show(state: State)
    fun observe(owner: LifecycleOwner, observer: Observer<State>)
}

interface ListCommunication {
    fun getList(): List<UiItem>
    fun showList(list: List<UiItem>)
    fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>)
    fun getDiffResult(): DiffUtil.DiffResult
}