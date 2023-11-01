package com.maxim.getdatafromapiandshow2.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxim.getdatafromapiandshow2.domain.Interactor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val interactor: Interactor,
    //todo fix public field (used in main activity)
    val communication: Communication,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    fun getItem() {
        viewModelScope.launch(dispatcher) {
            communication.show(State.Progress)
            interactor.getFact().mapToUi().show(communication)
        }
    }

    fun saveItem() {
        viewModelScope.launch(dispatcher) {
            interactor.saveFact()
            communication.showList(interactor.getAllFacts().map { it.mapToUi() })
        }
    }

    fun getItemList() {
        viewModelScope.launch(dispatcher) {
            communication.showList(interactor.getAllFacts().map { it.mapToUi() })
        }
    }

    fun removeItem(text: String) {
        viewModelScope.launch(dispatcher) {
            interactor.removeItem(text)
            communication.showList(interactor.getAllFacts().map { it.mapToUi() })
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<State>) {
        communication.observe(owner, observer)
    }

    fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
        communication.observeList(owner, observer)
    }
}