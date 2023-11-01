package com.maxim.getdatafromapiandshow2.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxim.getdatafromapiandshow2.data.DataItem

@Entity(tableName = "facts")
data class FactRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val text: String
) {
    //todo not override method
    fun toDataItem(): DataItem = DataItem.BaseDataItem(text)
}