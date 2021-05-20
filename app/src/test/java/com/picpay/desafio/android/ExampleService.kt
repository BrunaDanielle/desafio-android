package com.picpay.desafio.android

import com.picpay.desafio.android.domain.models.User

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}