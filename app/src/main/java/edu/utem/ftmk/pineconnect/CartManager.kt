package edu.utem.ftmk.pineconnect

object CartManager {
    val cartItems = mutableListOf<Pineapple>()

    fun addItem(item: Pineapple) {
        val existing = cartItems.find { it.name == item.name }
        if (existing != null) {
            existing.quantity += item.quantity
        } else {
            cartItems.add(item)
        }
    }

    fun getItems(): List<Pineapple> = cartItems
    fun getTotal(): Double = cartItems.sumOf { it.price * it.quantity }
}
