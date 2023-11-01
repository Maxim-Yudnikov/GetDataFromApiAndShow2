package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.domain.DomainItem

interface DataItem {
    fun toDomainItem(): DomainItem
    fun getText(): String
    data class BaseDataItem(private val text: String) : DataItem {
        override fun toDomainItem(): DomainItem = DomainItem.BaseDomainItem(text)
        override fun getText() = text
    }

    data class FailedDataItem(private val text: String) : DataItem {
        override fun toDomainItem(): DomainItem = DomainItem.FailedDomainItem(text)
        override fun getText() = text
    }
}