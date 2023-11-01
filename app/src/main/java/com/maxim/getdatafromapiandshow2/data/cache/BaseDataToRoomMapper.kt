package com.maxim.getdatafromapiandshow2.data.cache

import com.maxim.getdatafromapiandshow2.data.DataItem

class BaseDataToRoomMapper: DataToRoomMapper {
    override fun map(data: DataItem): FactRoomModel {
        return FactRoomModel(null, data.getText())
    }
}