package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.cache.FactRoomModel
import com.maxim.getdatafromapiandshow2.domain.DomainItem

interface DataItem {
    fun toDomainItem(): DomainItem
    //todo data to room mapper
    fun toRoomItem(): FactRoomModel

    abstract class Abstract(private val text: String) : DataItem {
        override fun toRoomItem() = FactRoomModel(null, text, System.currentTimeMillis())
    }

    data class BaseDataItem(private val text: String) : Abstract(text) {
        override fun toDomainItem(): DomainItem = DomainItem.BaseDomainItem(text)

    }

    data class FailedDataItem(private val text: String) : Abstract(text) {
        override fun toDomainItem(): DomainItem = DomainItem.FailedDomainItem(text)
    }
}