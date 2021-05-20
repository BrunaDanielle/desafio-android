package com.picpay.desafio.android.infrastructure.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.picpay.desafio.android.infrastructure.repository.ContactRepository
import com.picpay.desafio.android.presentation.viewmodel.ContactViewModel

class ContactViewModelProviderFactory(private val contactRepository: ContactRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactViewModel(contactRepository) as T
    }
}