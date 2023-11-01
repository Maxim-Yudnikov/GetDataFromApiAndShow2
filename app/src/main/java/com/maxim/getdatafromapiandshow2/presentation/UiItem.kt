package com.maxim.getdatafromapiandshow2.presentation

import android.widget.TextView

interface UiItem {
    fun same(item: UiItem): Boolean
    fun show(communication: Communication)
    fun show(textView: TextView)
    class BaseUiItem(private val text: String): Abstract() {
        override fun show(communication: Communication) = communication.show(State.Success(text))
        override fun show(textView: TextView) {
            textView.text = text
        }

        override fun same(item: UiItem): Boolean {
            return item is BaseUiItem && item.text == text
        }
    }

    class FailedUiItem(private val text: String): Abstract() {
        override fun show(communication: Communication) = communication.show(State.Failed(text))
    }

    abstract class Abstract : UiItem {
        override fun same(item: UiItem): Boolean = false
        override fun show(textView: TextView) {}
    }
}