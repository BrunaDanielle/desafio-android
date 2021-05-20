package com.picpay.desafio.android

import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.infrastructure.interfaces.network.PicPayService

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}