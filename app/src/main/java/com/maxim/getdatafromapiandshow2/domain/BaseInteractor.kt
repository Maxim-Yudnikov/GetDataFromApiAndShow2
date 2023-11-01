package com.maxim.getdatafromapiandshow2.domain

import com.maxim.getdatafromapiandshow2.data.Repository
import com.maxim.getdatafromapiandshow2.data.net.FailureHandler
import com.maxim.getdatafromapiandshow2.presentation.UiItem

class BaseInteractor(
    private val repository: Repository,
    private val failureHandler: FailureHandler
): Interactor {
    override suspend fun getFact(): DomainItem {
        return try {
            repository.getItem()
        } catch (e: Exception) {
            DomainItem.FailedDomainItem(failureHandler.handle(e).getMessage())
        }
    }

    override suspend fun getAllFacts(): List<DomainItem> {
        return try {
            repository.getAllItems()
        } catch (e: Exception) {
            listOf(DomainItem.FailedDomainItem(failureHandler.handle(e).getMessage()))
        }
    }

    override suspend fun saveFact() {
        repository.saveItem()
    }
}