package com.picpay.desafio.android.infrastructure.repository

import com.picpay.desafio.android.domain.interfaces.ContactRepository
import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.domain.models.UserEntity
import com.picpay.desafio.android.domain.util.Resource
import com.picpay.desafio.android.framework.retrofit.RetrofitInstance
import com.picpay.desafio.android.framework.room.ContactDatabase

class ContactRepositoryImpl(private val db: ContactDatabase) : ContactRepository {

    private suspend fun saveContacts(users: List<UserEntity>) {
        db.getContactDao().upsert(users)
    }

    override suspend fun getSavedContacts(): Resource<List<User>> {
        val savedContacts = db.getContactDao().getAllContacts()

        savedContacts.run { savedContacts.isNotEmpty() }.let {isNotEmpty ->
            if(isNotEmpty){
                return Resource.Success(savedContacts.map {
                    it.toUser()
                })
            }
        }

        try {
            val response = RetrofitInstance.api.getUsers()
            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    saveContacts(resultResponse)
                    return Resource.Success(resultResponse.map {
                        it.toUser()
                    })
                }
            }
        }catch (t: Throwable){
            return Resource.Error()
        }

        return Resource.Error()
    }
}

private fun UserEntity.toUser() = User(
    img = img,
    name = name,
    id = id,
    username = username
)

