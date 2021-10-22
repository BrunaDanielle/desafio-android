package com.picpay.desafio.android.domain.usecase

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.infrastructure.repository.ContactRepository

class ContactsUseCase(private val repository: ContactRepository) {

    suspend operator fun invoke(): LiveData<Unit> {
        repository.getUsers()
        return repository.getSavedContacts()
    }
}