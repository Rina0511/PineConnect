package edu.utem.ftmk.pineconnect

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pineapple(
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val origin: String = "",
    val price: Double = 0.0,
    val size: String = "",
    var quantity: Int = 1
) : Parcelable
