package com.maxim.getdatafromapiandshow2.data.cache

interface CacheModule {
    fun provideDataBase(): RoomFactsDatabase
}