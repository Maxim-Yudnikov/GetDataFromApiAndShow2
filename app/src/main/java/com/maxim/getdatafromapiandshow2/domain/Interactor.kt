package com.maxim.getdatafromapiandshow2.domain

interface Interactor {
    suspend fun getItem(): DomainItem
    suspend fun getItemsList(): List<DomainItem>
    suspend fun saveItem()
    suspend fun removeItem(text: String)
}