package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.domain.DomainItem

interface Repository {
    suspend fun getItem(): DomainItem
    suspend fun getAllItems(): List<DomainItem>
    suspend fun saveItem()
}