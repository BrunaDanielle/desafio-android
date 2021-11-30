package com.picpay.desafio.android.di

import android.content.Context
import com.picpay.desafio.android.domain.interfaces.ContactRepository
import com.picpay.desafio.android.domain.usecase.ContactsUseCase
import com.picpay.desafio.android.framework.room.ContactDatabase
import com.picpay.desafio.android.infrastructure.repository.ContactRepositoryImpl
import com.picpay.desafio.android.presentation.viewmodel.ContactViewModel
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ContactsModule {

    val presentationModule = module {
        viewModel {
            ContactViewModel(get())
        }

        factory { UserListAdapter() }
        factory { ContactsUseCase(repository = get()) }
        factory<ContactRepository> { ContactRepositoryImpl(db = getContactDatabase(androidContext())) }
    }

    private fun getContactDatabase(context: Context) = ContactDatabase(context = context)
}
