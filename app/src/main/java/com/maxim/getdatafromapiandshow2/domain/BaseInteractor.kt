package com.maxim.getdatafromapiandshow2.domain

import com.maxim.getdatafromapiandshow2.data.Repository
import com.maxim.getdatafromapiandshow2.data.net.FailureHandler

class BaseInteractor(
    private val repository: Repository,
    private val failureHandler: FailureHandler
): Interactor {
    override suspend fun getItem(): DomainItem {
        return try {
            repository.getItem()
        } catch (e: Exception) {
            DomainItem.FailedDomainItem(failureHandler.handle(e).getMessage())
        }
    }

    override suspend fun getItemsList(): List<DomainItem> {
        return try {
            repository.getAllItems()
        } catch (e: Exception) {
            listOf(DomainItem.FailedDomainItem(failureHandler.handle(e).getMessage()))
        }
    }

    override suspend fun saveItem() {
        repository.saveItem()
    }

    override suspend fun removeItem(text: String) {
        repository.removeItem(text)
    }
}