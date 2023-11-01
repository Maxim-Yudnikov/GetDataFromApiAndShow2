package com.maxim.getdatafromapiandshow2

interface UiItem {
    fun show(communication: Communication)
    class BaseUiItem(private val text: String): UiItem {
        override fun show(communication: Communication) = communication.show(State.Success(text))
    }

    class FailedUiItem(private val text: String): UiItem {
        override fun show(communication: Communication) = communication.show(State.Failed(text))
    }
}