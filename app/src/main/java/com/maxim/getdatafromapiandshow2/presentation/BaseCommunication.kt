package com.maxim.getdatafromapiandshow2.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil

class BaseCommunication : Communication {
    private val liveData = MutableLiveData<State>()
    private val listLiveData = MutableLiveData<List<UiItem>>()
    private lateinit var diffResult: DiffUtil.DiffResult
    override fun show(state: State) {
        liveData.value = state
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<State>) {
        liveData.observe(owner, observer)
    }

    override fun getList(): List<UiItem> {
        return listLiveData.value ?: emptyList()
    }

    override fun showList(list: List<UiItem>) {
        val callback = DiffUtilCallback(listLiveData.value ?: emptyList(), list)
        diffResult = DiffUtil.calculateDiff(callback)
        listLiveData.value = list
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
        listLiveData.observe(owner, observer)
    }

    override fun getDiffResult() = diffResult
}