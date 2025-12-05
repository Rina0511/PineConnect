package edu.utem.ftmk.pineconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private var cartItems: MutableList<Pineapple>,
    private val onQuantityChanged: (Int, Pineapple) -> Unit,
    private val onDelete: (Pineapple) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cartItemImage)
        val name: TextView = itemView.findViewById(R.id.cartItemName)
        val price: TextView = itemView.findViewById(R.id.cartItemPrice)
        val quantity: TextView = itemView.findViewById(R.id.cartItemQuantity)
        val btnIncrease: Button = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease: Button = itemView.findViewById(R.id.btnDecrease)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.name.text = item.name

        // ✅ Use string resource for price
        holder.price.text = holder.itemView.context.getString(R.string.price_format, item.price)

        holder.quantity.text = item.quantity.toString()

        Glide.with(holder.image.context).load(item.imageUrl).into(holder.image)

        holder.btnIncrease.setOnClickListener {
            item.quantity++
            holder.quantity.text = item.quantity.toString()
            onQuantityChanged(position, item)
            notifyItemChanged(position) // ✅ more efficient than notifyDataSetChanged
        }

        holder.btnDecrease.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                holder.quantity.text = item.quantity.toString()
                onQuantityChanged(position, item)
                notifyItemChanged(position) // ✅ update only this row
            }
        }

        holder.btnDelete.setOnClickListener {
            val removedPosition = holder.adapterPosition
            if (removedPosition != RecyclerView.NO_POSITION) {
                onDelete(item)
                cartItems.removeAt(removedPosition)
                notifyItemRemoved(removedPosition) // ✅ efficient removal
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size

    // ✅ Update with fine-grained notifications
    fun updateData(newItems: MutableList<Pineapple>) {
        cartItems = newItems
        notifyDataSetChanged() // fallback, but try to use notifyItemInserted/Removed in CartFragment
    }
}
