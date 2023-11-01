package com.maxim.getdatafromapiandshow2.data.domain

interface BaseFailure {
    fun getMessage(): String
}

class NoConnectionError : BaseFailure {
    override fun getMessage() = "No connection"
}

class ServiceUnavailableError: BaseFailure {
    override fun getMessage() = "Service unavailable"
}

class UnknownError: BaseFailure {
    override fun getMessage() = "Unknown rrror"
}