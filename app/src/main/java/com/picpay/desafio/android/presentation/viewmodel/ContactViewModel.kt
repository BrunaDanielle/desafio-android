package com.picpay.desafio.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.infrastructure.repository.ContactRepository

class ContactViewModel(val contactRepository: ContactRepository): ViewModel() {

}