package ru.mrfiring.focusapp.presentation.detail

import ru.mrfiring.focusapp.domain.usecase.GetContactWithIdFromStorageUseCase
import ru.mrfiring.focusapp.domain.usecase.UpdateContactUseCase
import ru.mrfiring.focusapp.presentation.BaseViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getContactWithIdFromStorageUseCase: GetContactWithIdFromStorageUseCase,
    private val updateContactUseCase: UpdateContactUseCase
) : BaseViewModel() {

}