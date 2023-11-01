package com.maxim.getdatafromapiandshow2.data.cache

import com.maxim.getdatafromapiandshow2.data.DataItem

class BaseCachedItem: CachedItem {
    private var item: DataItem? = null
    override fun saveItem(dataItem: DataItem) {
        item = dataItem
    }

    override fun getItem(): DataItem {
        return item!!
    }

    override fun clear() {
        item = null
    }
}