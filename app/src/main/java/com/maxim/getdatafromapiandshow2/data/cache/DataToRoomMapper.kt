package com.maxim.getdatafromapiandshow2.data.cache

import com.maxim.getdatafromapiandshow2.data.DataItem

interface DataToRoomMapper {
    fun map(data: DataItem): FactRoomModel
}