package com.maxim.getdatafromapiandshow2

interface State {
    data class Success(private val text: String): State
}