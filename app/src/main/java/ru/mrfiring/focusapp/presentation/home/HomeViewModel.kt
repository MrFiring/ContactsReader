package ru.mrfiring.focusapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.DomainContact
import ru.mrfiring.focusapp.domain.usecase.FetchContactsFromProviderUseCase
import ru.mrfiring.focusapp.domain.usecase.GetContactsFromStorageUseCase
import ru.mrfiring.focusapp.domain.usecase.RemoveContactUseCase
import ru.mrfiring.focusapp.presentation.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val fetchContactsFromProviderUseCase: FetchContactsFromProviderUseCase,
    private val getContactsFromStorageUseCase: GetContactsFromStorageUseCase,
    private val removeContactUseCase: RemoveContactUseCase
) : BaseViewModel() {

    private val _contacts = MutableLiveData<List<DomainContact>>()
    val contacts: LiveData<List<DomainContact>>
        get() = _contacts

    init {

        getContactsFromStorageUseCase(UseStorage.FILE)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _contacts.postValue(it)
            }
            .untilDestroy()

    }

}