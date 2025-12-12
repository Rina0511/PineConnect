package edu.utem.ftmk.pineconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PineappleAdapter(
    private var items: List<Pineapple>,
    private val onItemClick: (Pineapple) -> Unit
) : RecyclerView.Adapter<PineappleAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.itemName)
        val price: TextView = view.findViewById(R.id.itemPrice)
        val image: ImageView = view.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pineapple, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pineapple = items[position]
        holder.name.text = pineapple.name
        holder.price.text = holder.itemView.context.getString(R.string.price_format, pineapple.price)

        Glide.with(holder.image.context)
            .load(pineapple.imageUrl)
            .into(holder.image)

        // ✅ Click listener on whole item
        holder.itemView.setOnClickListener { onItemClick(pineapple) }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<Pineapple>) {
        items = newItems
        notifyDataSetChanged()
    }
}
