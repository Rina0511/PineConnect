package edu.utem.ftmk.pineconnect

data class Survey(
    val id: String = "",
    val period: String = "Unknown Period",
    val isCompleted: Boolean = false
    // You can add more fields like dateCreated, type, etc.
)