package com.example.dadjokes.business.domain.mode

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Joke(
    var id: String,
    var joke: String,
    var status: String,
    var isFavourite: Boolean? = null
):  Parcelable
