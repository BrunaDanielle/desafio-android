package com.picpay.desafio.android.infrastructure.repository

import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.framework.room.ContactDatabase

class ContactRepository(val db: ContactDatabase) {

    suspend fun getUsers()= "" //TODO: chamada retrofit

    suspend fun upsert(users: List<User>) = db.getContactDao().upsert(users)

    fun getSavedContacts() = db.getContactDao().getAllContacts()
}