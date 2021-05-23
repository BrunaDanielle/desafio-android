package com.picpay.desafio.android.infrastructure.repository

import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.framework.retrofit.RetrofitInstance
import com.picpay.desafio.android.framework.room.ContactDatabase

class ContactRepository(private val db: ContactDatabase) {

    suspend fun getUsers()= RetrofitInstance.api.getUsers()

    suspend fun upsert(users: List<User>) = db.getContactDao().upsert(users)

    fun getSavedContacts() = db.getContactDao().getAllContacts()
}