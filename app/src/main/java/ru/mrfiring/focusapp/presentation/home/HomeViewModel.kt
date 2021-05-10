package ru.mrfiring.focusapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.mrfiring.focusapp.domain.UseStorage
import ru.mrfiring.focusapp.domain.model.DomainContact
import ru.mrfiring.focusapp.domain.usecase.*
import ru.mrfiring.focusapp.presentation.BaseViewModel
import ru.mrfiring.focusapp.util.SingleLiveEvent
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val fetchContactsFromProviderUseCase: FetchContactsFromProviderUseCase,
    private val getContactsFromStorageUseCase: GetContactsFromStorageUseCase,
    private val removeContactUseCase: RemoveContactUseCase,
    private val getIsFirstLaunchPassedUseCase: GetIsFirstLaunchPassedUseCase,
    private val getUseStorageUseCase: GetUseStorageUseCase,
    private val saveFirstLaunchPassedUseCase: SaveFirstLaunchPassedUseCase,
    private val saveUseDBStorageByDefaultUseCase: SaveUseStorageUseCase
) : BaseViewModel() {

    private val _prefsState = SingleLiveEvent<PrefsState>()
    val prefState: LiveData<PrefsState>
        get() = _prefsState

    private val _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState

    private val _navigateToDetail = SingleLiveEvent<Int>()
    val navigateToDetail: LiveData<Int>
        get() = _navigateToDetail

    private val _showStorageChangedToast = SingleLiveEvent<UseStorage>()
    val showStorageChangedToast: LiveData<UseStorage>
        get() = _showStorageChangedToast

    fun initialLoading() {
        Single.zip(
            getIsFirstLaunchPassedUseCase(),
            getUseStorageUseCase()
        ) { isFirstRun, useStorage ->
            PrefsState(isFirstRun, useStorage)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state -> _prefsState.postValue(state) }
            .untilDestroy()
    }

    fun respondToPrefsState(prefsState: PrefsState) {
        _homeState.postValue(
            HomeState.Loading
        )

        with(prefsState) {
            if (!isFirstLaunchPassed) {
                fetchContacts(useStorage)
                firstLaunchPassed(this)
            }
            getContactsFromStorage(useStorage)
        }
    }

    fun onNavigateToDetail(contactId: Int) {
        _navigateToDetail.postValue(contactId)
    }

    fun removeContact(domainContact: DomainContact) {
        _prefsState.value?.let {
            removeContactUseCase(
                contact = domainContact,
                useStorage = it.useStorage
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .untilDestroy()
        }
    }

    fun changeStorageType() {
        _prefsState.value?.let { oldPrefs ->
            val newUseStorage = oldPrefs.useStorage.next()

            _prefsState.value = oldPrefs.copy(
                useStorage = newUseStorage
            )

            _showStorageChangedToast.postValue(newUseStorage)
            savePrefs()
        }
    }

    private fun savePrefs() {
        _prefsState.value?.let {
            Completable.mergeArray(
                saveFirstLaunchPassedUseCase(it.isFirstLaunchPassed),
                saveUseDBStorageByDefaultUseCase(it.useStorage)
            )
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .untilDestroy()
        }
    }

    private fun fetchContacts(useStorage: UseStorage) {
        fetchContactsFromProviderUseCase(useStorage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .untilDestroy()
    }

    private fun getContactsFromStorage(useStorage: UseStorage) {
        getContactsFromStorageUseCase(useStorage)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it.isEmpty()) {
                    fetchContacts(useStorage)
                }
            }
            .subscribe {
                _homeState.postValue(
                    HomeState.Success(it)
                )
            }
            .untilDestroy()
    }

    private fun firstLaunchPassed(prefsState: PrefsState) {
        _prefsState.postValue(
            prefsState.copy(
                isFirstLaunchPassed = true
            )
        )
    }


    override fun onCleared() {
        savePrefs()
        super.onCleared()
    }
}