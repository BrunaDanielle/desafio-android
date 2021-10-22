package com.picpay.desafio.android

import com.picpay.desafio.android.domain.models.UserEntity
import com.picpay.desafio.android.infrastructure.interfaces.network.PicPayService

class ExampleService(
    private val service: PicPayService
) {

    suspend fun example(): List<UserEntity>? {
      service.getUsers()

        return emptyList()
    }
}