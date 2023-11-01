package com.maxim.getdatafromapiandshow2.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun insertFact(fact: FactRoomModel)

    @Query("SELECT * FROM facts ORDER BY time DESC")
    suspend fun getAllFacts(): List<FactRoomModel>

    @Query("DELETE from facts WHERE text IS :text")
    suspend fun removeFact(text: String)
}