package com.maxim.getdatafromapiandshow2.domain

import com.maxim.getdatafromapiandshow2.domain.DomainItem

interface Interactor {
    suspend fun getFact(): DomainItem
}