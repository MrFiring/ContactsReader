package ru.mrfiring.focusapp.presentation.home

import ru.mrfiring.focusapp.domain.model.DomainContact

sealed class HomeState {

    object Loading : HomeState()

    data class Success(val contacts: List<DomainContact>) : HomeState()
}
