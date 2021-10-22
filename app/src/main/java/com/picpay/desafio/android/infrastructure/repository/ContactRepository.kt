package com.picpay.desafio.android.infrastructure.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.domain.models.UserEntity
import com.picpay.desafio.android.framework.retrofit.RetrofitInstance
import com.picpay.desafio.android.framework.room.ContactDatabase

class ContactRepository(private val db: ContactDatabase) {

    suspend fun getUsers() {
        val response = RetrofitInstance.api.getUsers()
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
               upsert(resultResponse)
            }
        }
    }

    private suspend fun upsert(users: List<UserEntity>) {
        db.getContactDao().upsert(users)
    }

    fun getSavedContacts() : LiveData<Unit> {
        return db.getContactDao().getAllContacts().map {
            it.forEach { user ->
                user.toUser()
            }
        }
    }
}

private fun UserEntity.toUser() = User(
    img = img,
    name = name,
    id = id,
    username = username
)

