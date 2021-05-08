package ru.mrfiring.focusapp.data.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ContactsDao {

    @Query("select * from databasecontact")
    fun getContactsFlowable(): Flowable<List<DatabaseContact>>

    @Query("select * from databasecontact where id = :id limit 1")
    fun getContactById(id: Int): Single<DatabaseContact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllContacts(items: List<DatabaseContact>): Completable

    @Update
    fun updateContact(item: DatabaseContact): Completable

    @Query("delete from databasecontact where id = :id")
    fun deleteContact(id: Int): Completable

    @Query("delete from databasecontact")
    fun deleteAllContacts(): Completable

}

@Database(
    entities = [DatabaseContact::class],
    version = 2
)
abstract class ContactsDatabase : RoomDatabase() {
    abstract val contactsDao: ContactsDao
}