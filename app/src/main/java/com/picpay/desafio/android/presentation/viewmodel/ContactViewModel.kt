package com.picpay.desafio.android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.domain.usecase.ContactsUseCase
import com.picpay.desafio.android.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactViewModel(
    private val contactUserCase: ContactsUseCase
) : ViewModel() {

    private val _users: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val users: LiveData<Resource<List<User>>> = _users

    init {
        safeGetUsersCall()
    }

    fun reloadContacts() = safeGetUsersCall()

    private fun safeGetUsersCall() {
        _users.postValue(Resource.Loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _users.postValue(contactUserCase.getContacts())
            }
        }
    }
}