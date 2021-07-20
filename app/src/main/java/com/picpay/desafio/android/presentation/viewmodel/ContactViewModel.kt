package com.picpay.desafio.android.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.ContactsApplication
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.domain.util.Resource
import com.picpay.desafio.android.infrastructure.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class ContactViewModel(app: Application, private val contactRepository: ContactRepository) : AndroidViewModel(app) {
    val users: MutableLiveData<Resource<List<User>>> = MutableLiveData()

    init {
        getSavedContacts()
    }

    fun getUsers(){
        safeGetUsersCall()
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

    private fun safeGetUsersCall(){
        users.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val response = contactRepository.getUsers()
                        users.postValue(handleUsersResponse(response))
                    }
                }
            }else{
                users.postValue(Resource.Error(getApplication<ContactsApplication>().getString(R.string.no_internet_connection)))
            }
        }catch (t: Throwable){
            when(t){
                is IOException ->  users.postValue(Resource.Error(getApplication<ContactsApplication>().getString(R.string.network_failure)))
                else -> users.postValue(Resource.Error(getApplication<ContactsApplication>().getString(R.string.data_failure)))
            }
        }
    }

    fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<ContactsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run{
                return when(type){
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}