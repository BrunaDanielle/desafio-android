package com.picpay.desafio.android.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.picpay.desafio.android.R
import com.picpay.desafio.android.presentation.viewmodel.ContactViewModel
import com.picpay.desafio.android.ui.view.ContactActivity

class ContactsFragment : Fragment(R.layout.fragment_contacts) {
    lateinit var viewModel: ContactViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ContactActivity).viewModel
    }
}