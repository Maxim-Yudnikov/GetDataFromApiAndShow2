package com.maxim.getdatafromapiandshow2.domain

import com.maxim.getdatafromapiandshow2.data.DataItem
import com.maxim.getdatafromapiandshow2.presentation.UiItem

interface DomainItem {
    fun mapToUi(): UiItem
    fun mapToData() : DataItem

    data class BaseDomainItem(private val text: String) : DomainItem {
        override fun mapToUi() = UiItem.BaseUiItem(text)
        override fun mapToData() = DataItem.BaseDataItem(text)
    }

    data class FailedDomainItem(private val text: String) : DomainItem {
        override fun mapToUi() = UiItem.FailedUiItem(text)
        override fun mapToData() = DataItem.FailedDataItem(text)
    }
}