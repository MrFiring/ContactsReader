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

    private var _prefsState: PrefsState? = null

    private val _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState

    private val _navigateToDetail = SingleLiveEvent<Int>()
    val navigateToDetail: LiveData<Int>
        get() = _navigateToDetail

    private val _showStorageChanged = SingleLiveEvent<UseStorage>()
    val showStorageChangedToast: LiveData<UseStorage>
        get() = _showStorageChanged

    fun initialLoading() {
        Single.zip(
            getIsFirstLaunchPassedUseCase(),
            getUseStorageUseCase()
        ) { isFirstRun, useStorage ->
            PrefsState(isFirstRun, useStorage)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                _prefsState = state
                respondToPrefsStateChange()
            }
            .untilDestroy()
    }

    private fun respondToPrefsStateChange() {
        _prefsState?.let { prefsState ->
            with(prefsState) {
                if (!isFirstLaunchPassed) {
                    _homeState.postValue(HomeState.Loading)

                    fetchContacts(useStorage)
                    firstLaunchPassed(this)
                }
                getContactsFromStorage(useStorage)
            }
        }

    }

    fun onNavigateToDetail(contactId: Int) {
        _navigateToDetail.postValue(contactId)
    }

    fun removeContact(domainContact: DomainContact) {
        _prefsState?.let {
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
        _prefsState?.let { oldPrefs ->
            val newUseStorage = oldPrefs.useStorage.next()

            _prefsState = oldPrefs.copy(
                useStorage = newUseStorage
            )

            _showStorageChanged.postValue(newUseStorage)
            respondToPrefsStateChange()
            savePrefs()
        }
    }

    private fun savePrefs() {
        _prefsState?.let {
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
        _prefsState = prefsState.copy(
            isFirstLaunchPassed = true
        )
    }


    override fun onCleared() {
        savePrefs()
        super.onCleared()
    }
}