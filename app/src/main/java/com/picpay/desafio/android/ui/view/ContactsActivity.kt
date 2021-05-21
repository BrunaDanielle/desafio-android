package com.picpay.desafio.android.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.picpay.desafio.android.R
import com.picpay.desafio.android.framework.room.ContactDatabase
import com.picpay.desafio.android.infrastructure.factory.ContactViewModelProviderFactory
import com.picpay.desafio.android.infrastructure.repository.ContactRepository
import com.picpay.desafio.android.presentation.viewmodel.ContactViewModel

class ContactsActivity : AppCompatActivity(R.layout.activity_contacts) {

    lateinit var viewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contactRepository = ContactRepository(ContactDatabase(this))
        val viewModelProviderFactory = ContactViewModelProviderFactory(contactRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ContactViewModel::class.java)
    }
}