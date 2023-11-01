package com.maxim.getdatafromapiandshow2.data.net

import com.maxim.getdatafromapiandshow2.data.DataItem

data class FactServiceModel(
    private val fact: String
) {
    //todo not override fun
    fun toDataModel(): DataItem = DataItem.BaseDataItem(fact)
}