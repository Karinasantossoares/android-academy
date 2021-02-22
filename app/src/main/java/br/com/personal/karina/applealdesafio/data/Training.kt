package br.com.personal.karina.applealdesafio.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Training(
    var id: String? = null,
    var name: String = "",
    var description: String = "",
    var date: Date? = null
) :Parcelable