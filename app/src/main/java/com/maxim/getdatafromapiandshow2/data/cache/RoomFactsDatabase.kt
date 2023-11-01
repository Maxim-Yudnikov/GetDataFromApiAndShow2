package com.maxim.getdatafromapiandshow2.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FactRoomModel::class], version = 1)
abstract class RoomFactsDatabase : RoomDatabase() {
    abstract fun dao(): Dao
}