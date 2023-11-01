package com.maxim.getdatafromapiandshow2.domain

interface Interactor {
    suspend fun getFact(): DomainItem
    suspend fun getAllFacts(): List<DomainItem>
    suspend fun saveFact()
    suspend fun removeItem(text: String)
}