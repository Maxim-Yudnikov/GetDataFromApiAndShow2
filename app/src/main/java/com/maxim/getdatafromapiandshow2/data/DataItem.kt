package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.cache.FactRoomModel
import com.maxim.getdatafromapiandshow2.domain.DomainItem

interface DataItem {
    fun toDomainItem(): DomainItem
    //todo data to room mapper
    fun toRoomItem(): FactRoomModel

    data class BaseDataItem(private val text: String) : DataItem {
        override fun toDomainItem(): DomainItem = DomainItem.BaseDomainItem(text)
        override fun toRoomItem() = FactRoomModel(null, text)
    }

    data class FailedDataItem(private val text: String) : DataItem {
        override fun toDomainItem(): DomainItem = DomainItem.FailedDomainItem(text)
        override fun toRoomItem() = FactRoomModel(null, text)
    }
}