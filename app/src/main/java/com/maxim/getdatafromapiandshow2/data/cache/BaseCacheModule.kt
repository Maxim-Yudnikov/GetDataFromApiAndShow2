package com.maxim.getdatafromapiandshow2.data.cache

import android.content.Context
import androidx.room.Room

class BaseCacheModule(private val context: Context): CacheModule {
    private val database by lazy {
        return@lazy Room.databaseBuilder(
            context,
            RoomFactsDatabase::class.java,
            "facts_database"
        ).build()
    }

    override fun provideDataBase() = database
}