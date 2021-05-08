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
    private val getIsUseDBStorageByDefaultUseCase: GetIsUseDBStorageByDefaultUseCase,
    private val saveFirstLaunchPassedUseCase: SaveFirstLaunchPassedUseCase,
    private val saveUseDBStorageByDefaultUseCase: SaveUseDBStorageByDefaultUseCase
) : BaseViewModel() {

    private val _prefsState = SingleLiveEvent<PrefsState>()
    val prefState: LiveData<PrefsState>
        get() = _prefsState

    private val _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState

    private val _navigateToDetail = SingleLiveEvent<DomainContact>()
    val navigateToDetail: LiveData<DomainContact>
        get() = _navigateToDetail

    private val _showStorageChangedToast = SingleLiveEvent<UseStorage>()
    val showStorageChangedToast: LiveData<UseStorage>
        get() = _showStorageChangedToast

    fun initialLoading() {
        Single.zip(
            getIsFirstLaunchPassedUseCase(),
            getIsUseDBStorageByDefaultUseCase()
        ) { isFirstRun, isUseDb ->
            PrefsState(isFirstRun, isUseDb)
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
            if (!isFirstLaunchPassed && isUseDBStorage) {
                fetchContacts(isUseDBStorage)
                firstLaunchPassed(this)
            }
            getContactsFromStorage(isUseDBStorage)
        }
    }

    fun onNavigateToDetail(domainContact: DomainContact) {
        _navigateToDetail.postValue(domainContact)
    }

    fun contactRemoved(domainContact: DomainContact) {
        _prefsState.value?.let {
            removeContactUseCase(
                contact = domainContact,
                useStorage = if (it.isUseDBStorage) UseStorage.DATABASE else UseStorage.FILE
            )
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }

    fun storageTypeChanged() {
        _prefsState.value?.let { oldPrefs ->
            val newUseStorage = !oldPrefs.isUseDBStorage

            _prefsState.postValue(
                oldPrefs.copy(
                    isUseDBStorage = newUseStorage
                )
            )

            _showStorageChangedToast.postValue(
                if (newUseStorage) UseStorage.DATABASE else UseStorage.FILE
            )
        }
    }

    fun savePrefs() {
        _prefsState.value?.let {
            Completable.mergeArray(
                saveFirstLaunchPassedUseCase(it.isFirstLaunchPassed),
                saveUseDBStorageByDefaultUseCase(it.isUseDBStorage)
            )
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .untilDestroy()
        }
    }

    private fun fetchContacts(useDBStorage: Boolean) {
        val useStorage = if (useDBStorage) UseStorage.DATABASE else UseStorage.FILE
        fetchContactsFromProviderUseCase(useStorage)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .untilDestroy()
    }

    private fun getContactsFromStorage(useDBStorage: Boolean) {
        val useStorage = if (useDBStorage) UseStorage.DATABASE else UseStorage.FILE
        getContactsFromStorageUseCase(useStorage)
            .subscribeOn(AndroidSchedulers.mainThread())
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

}