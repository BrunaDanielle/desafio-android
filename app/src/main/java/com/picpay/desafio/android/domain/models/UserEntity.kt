package com.picpay.desafio.android.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "users"
)
@Parcelize
data class UserEntity(
    val img: String?,
    val name: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val username: String?
) : Parcelable