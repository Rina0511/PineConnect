package edu.utem.ftmk.pineconnect

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var cartRecycler: RecyclerView
    private lateinit var cartTotal: TextView
    private lateinit var checkoutButton: Button
    private lateinit var adapter: CartAdapter

    // This holds all items currently in the cart
    private var cartItems = mutableListOf<Pineapple>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartRecycler = view.findViewById(R.id.cartRecycler)
        cartTotal = view.findViewById(R.id.cartTotal)
        checkoutButton = view.findViewById(R.id.checkoutButton)

        val cartItems = CartManager.getItems().toMutableList()

        adapter = CartAdapter(
            cartItems,
            onQuantityChanged = { _, _ -> updateTotal() },
            onDelete = { item ->
                CartManager.cartItems.remove(item)
                adapter.updateData(CartManager.getItems().toMutableList())
                updateTotal()
            }
        )

        cartRecycler.layoutManager = LinearLayoutManager(requireContext())
        cartRecycler.adapter = adapter

        updateTotal()

        checkoutButton.setOnClickListener {
            // TODO: Checkout logic
        }
    }

    private fun updateTotal() {
        val total = CartManager.getTotal()
        cartTotal.text = getString(R.string.price_format, total)
    }

    // Helper to add items from ProductDetailsFragment
    fun addItemToCart(item: Pineapple) {
        // If item already exists, increase quantity
        val existing = cartItems.find { it.name == item.name }
        if (existing != null) {
            existing.quantity += item.quantity
        } else {
            cartItems.add(item)
        }
        adapter.updateData(cartItems)
        updateTotal()
    }
}
