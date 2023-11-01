package com.maxim.getdatafromapiandshow2.data.cache

import com.maxim.getdatafromapiandshow2.data.DataItem

interface CachedItem {
    fun saveItem(dataItem: DataItem)
    fun getItem(): DataItem
    fun clear()
}