package com.picpay.desafio.android.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val img: String?,
    val name: String?,
    val id: Int,
    val username: String?
) : Parcelable