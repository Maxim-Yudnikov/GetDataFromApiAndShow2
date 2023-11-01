package com.maxim.getdatafromapiandshow2

interface Interactor {
    suspend fun getFact(): DomainItem
}