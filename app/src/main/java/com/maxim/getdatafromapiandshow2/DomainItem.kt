package com.maxim.getdatafromapiandshow2

interface DomainItem {
    fun map(): UiItem

    class BaseDomainItem(private val text: String) : DomainItem {
        override fun map() = UiItem.BaseUiItem(text)
    }

    class FailedDomainItem(private val text: String) : DomainItem {
        override fun map() = UiItem.FailedUiItem(text)
    }
}