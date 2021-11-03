package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.interfaces.ContactRepository
import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.domain.util.Resource

class ContactsUseCase(private val repository: ContactRepository) {

    suspend fun getContacts(): Resource<List<User>> {
        return repository.getSavedContacts()
    }
}