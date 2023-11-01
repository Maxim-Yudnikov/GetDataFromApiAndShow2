package com.maxim.getdatafromapiandshow2.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun insertFact(fact: FactRoomModel)

    @Query("SELECT * FROM facts")
    suspend fun getAllFacts(): List<FactRoomModel>
}