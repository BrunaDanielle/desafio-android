package com.picpay.desafio.android.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.domain.util.Resource
import com.picpay.desafio.android.infrastructure.repository.ContactRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    val users: MutableLiveData<Resource<List<User>>> = MutableLiveData()

    init {
        getSavedContacts()
    }

    fun getUsers() = viewModelScope.launch {
        users.postValue(Resource.Loading())
        val response = contactRepository.getUsers()
        users.postValue(handleUsersResponse(response))
    }

    private fun handleUsersResponse(response: Response<List<User>>): Resource<List<User>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveContacts(user: List<User>) = viewModelScope.launch {
        contactRepository.upsert(user)
    }

    fun getSavedContacts() = contactRepository.getSavedContacts()
}