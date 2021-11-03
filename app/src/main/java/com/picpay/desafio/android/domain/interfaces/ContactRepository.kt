package com.picpay.desafio.android.domain.interfaces

import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.domain.util.Resource

interface ContactRepository {
    suspend fun getSavedContacts() : Resource<List<User>>
}