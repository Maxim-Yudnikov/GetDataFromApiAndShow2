package com.maxim.getdatafromapiandshow2.domain

import com.maxim.getdatafromapiandshow2.presentation.UiItem

interface DomainItem {
    fun map(): UiItem

    data class BaseDomainItem(private val text: String) : DomainItem {
        override fun map() = UiItem.BaseUiItem(text)
    }

    data class FailedDomainItem(private val text: String) : DomainItem {
        override fun map() = UiItem.FailedUiItem(text)
    }
}