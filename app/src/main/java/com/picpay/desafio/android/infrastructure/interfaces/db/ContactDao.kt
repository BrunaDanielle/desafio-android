package com.picpay.desafio.android.infrastructure.interfaces.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.domain.models.User

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(users: List<User>)

    @Query("SELECT * FROM users")
    fun getAllContacts(): LiveData<List<User>>
}