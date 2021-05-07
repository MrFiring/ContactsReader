package ru.mrfiring.focusapp.data.provider

import android.content.Context
import android.provider.ContactsContract
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.mrfiring.focusapp.util.findColumnAndGetString

class SystemContactsProviderImpl constructor(
    private val context: Context
) : ContactsProvider {

    private val projection = arrayOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER
    )

    override fun getContactsFromPhonebook(): Single<List<ProviderContact>> = Single.fromCallable {
        val resultList = mutableListOf<ProviderContact>()

        val resolver = context.contentResolver

        resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { phonesCursor ->
            while (phonesCursor.moveToNext()) {
                val name = phonesCursor.findColumnAndGetString(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
                val number = phonesCursor.findColumnAndGetString(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                )

                resultList.add(
                    ProviderContact(name ?: "", number ?: "")
                )
            }
        }

        return@fromCallable resultList.toList()
    }.observeOn(Schedulers.io())
}