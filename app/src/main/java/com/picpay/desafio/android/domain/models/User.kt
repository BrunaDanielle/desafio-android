package com.picpay.desafio.android.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "users"
)
data class User(
    val img: String?,
    val name: String?,
    @PrimaryKey
    val id: Int,
    val username: String?
) : Serializable