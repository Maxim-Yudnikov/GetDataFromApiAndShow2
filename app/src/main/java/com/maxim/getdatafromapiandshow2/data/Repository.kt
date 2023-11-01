package com.maxim.getdatafromapiandshow2.data

interface Repository {
    suspend fun getItem(): DataItem
}