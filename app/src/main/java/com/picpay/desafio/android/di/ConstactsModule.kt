package com.picpay.desafio.android.di

import com.picpay.desafio.android.ContactsApplication
import com.picpay.desafio.android.domain.usecase.ContactsUseCase
import com.picpay.desafio.android.framework.room.ContactDatabase
import com.picpay.desafio.android.infrastructure.repository.ContactRepository
import com.picpay.desafio.android.presentation.viewmodel.ContactViewModel
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ConstactsModule {

    val presentationModule = module {
        viewModel {
            ContactViewModel(
                app = ContactsApplication(),
                contactRepository = get()
            )
        }

        factory { UserListAdapter() }
        factory { ContactsUseCase(repository = get()) }
        factory { ContactRepository(db = ContactDatabase(context = androidContext())) }
    }
}
