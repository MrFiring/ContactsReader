package ru.mrfiring.focusapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.DomainContact
import ru.mrfiring.focusapp.domain.usecase.GetContactWithIdFromStorageUseCase
import ru.mrfiring.focusapp.domain.usecase.GetIsUseDBStorageByDefaultUseCase
import ru.mrfiring.focusapp.domain.usecase.UpdateContactUseCase
import ru.mrfiring.focusapp.presentation.BaseViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getContactWithIdFromStorageUseCase: GetContactWithIdFromStorageUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val getIsUseDBStorageByDefaultUseCase: GetIsUseDBStorageByDefaultUseCase
) : BaseViewModel() {

    private val _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState>
        get() = _detailState

    private val _useStorage = MutableLiveData<UseStorage>()
    val useStorage: LiveData<UseStorage>
        get() = _useStorage


    fun initialLoading() {
        _detailState.postValue(
            DetailState.Loading
        )

        getIsUseDBStorageByDefaultUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isDBStorage ->
                _useStorage.value = if (isDBStorage) UseStorage.DATABASE else UseStorage.FILE
            }
            .untilDestroy()
    }

    fun loadContact(id: Int, storage: UseStorage) {
        getContactWithIdFromStorageUseCase(id, storage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                _detailState.postValue(
                    DetailState.Success(item)
                )
            }
            .untilDestroy()
    }

    fun updateContact(domainContact: DomainContact) {
        useStorage.value?.let { storage ->
            updateContactUseCase(domainContact, storage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .untilDestroy()
        }
    }

}