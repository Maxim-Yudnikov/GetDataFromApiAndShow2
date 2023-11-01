package com.maxim.getdatafromapiandshow2.domain

import com.maxim.getdatafromapiandshow2.data.Repository

class BaseInteractor(
    private val repository: Repository
): Interactor {
    override suspend fun getFact(): DomainItem {
        return repository.getItem().toDomainItem()
    }
}