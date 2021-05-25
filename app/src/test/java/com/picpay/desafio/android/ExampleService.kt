package com.picpay.desafio.android

import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.infrastructure.interfaces.network.PicPayService

class ExampleService(
    private val service: PicPayService
) {

    suspend fun example(): List<User>? {
      service.getUsers()

        return emptyList()
    }
}