package com.picpay.desafio.android.framework.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picpay.desafio.android.domain.models.UserEntity
import com.picpay.desafio.android.infrastructure.interfaces.db.ContactDao

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun getContactDao(): ContactDao

    companion object{
        @Volatile
        private var instance: ContactDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ContactDatabase::class.java,
                "contact_db.db"
            ).build()
    }
}