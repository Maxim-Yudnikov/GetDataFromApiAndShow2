package com.maxim.getdatafromapiandshow2

interface DomainToUiMapper {
    fun map(text: String): UiItem
}