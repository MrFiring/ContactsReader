package ru.mrfiring.focusapp.presentation.detail

import ru.mrfiring.focusapp.domain.model.DomainContact

sealed class DetailState {
    object Loading : DetailState()

    data class Success(val contact: DomainContact) : DetailState()
}
