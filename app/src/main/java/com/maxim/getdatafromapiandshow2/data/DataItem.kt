package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.domain.DomainItem

interface DataItem {
    fun toDomainItem(): DomainItem
    data class BaseDataItem(private val text: String) : DataItem {
        override fun toDomainItem(): DomainItem = DomainItem.BaseDomainItem(text)
    }

    data class FailedDataItem(private val text: String) : DataItem {
        override fun toDomainItem(): DomainItem = DomainItem.FailedDomainItem(text)
    }
}