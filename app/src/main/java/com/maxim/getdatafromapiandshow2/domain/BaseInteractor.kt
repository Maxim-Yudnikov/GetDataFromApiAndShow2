package com.maxim.getdatafromapiandshow2.domain

import com.maxim.getdatafromapiandshow2.data.Repository
import com.maxim.getdatafromapiandshow2.data.net.FailureHandler

class BaseInteractor(
    private val repository: Repository,
    private val failureHandler: FailureHandler
): Interactor {
    override suspend fun getFact(): DomainItem {
        return try {
            repository.getItem().toDomainItem()
        } catch (e: Exception) {
            DomainItem.FailedDomainItem(failureHandler.handle(e).getMessage())
        }
    }
}