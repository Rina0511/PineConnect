package edu.utem.ftmk.pineconnect

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private lateinit var imageView: ImageView
    private lateinit var nameView: TextView
    private lateinit var priceView: TextView
    private lateinit var originView: TextView
    private lateinit var sizeView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var quantityView: EditText
    private lateinit var addToCartButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind views
        imageView = view.findViewById(R.id.productImage)
        nameView = view.findViewById(R.id.productName)
        priceView = view.findViewById(R.id.productPrice)
        originView = view.findViewById(R.id.productOrigin)
        sizeView = view.findViewById(R.id.productSize)
        descriptionView = view.findViewById(R.id.productDescription)
        quantityView = view.findViewById(R.id.quantityInput)
        addToCartButton = view.findViewById(R.id.addToCartButton)

        @Suppress("DEPRECATION")
        val product = arguments?.getParcelable<Pineapple>("product")

        product?.let {
            nameView.text = it.name
            priceView.text = getString(R.string.price_format, it.price)
            originView.text = getString(R.string.origin_format, it.origin)
            sizeView.text = getString(R.string.size_format, it.size)
            descriptionView.text = it.description
            Glide.with(this).load(it.imageUrl).into(imageView)
        }

        addToCartButton.setOnClickListener {
            val qty = quantityView.text.toString().toIntOrNull() ?: 1
            product?.let {
                CartManager.addItem(it.copy(quantity = qty)) // ✅ shared cart
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, CartFragment(), "CartFragment")
                    .addToBackStack(null)
                    .commit()
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(product: Pineapple): ProductDetailFragment {
            return ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("product", product)
                }
            }
        }
    }
}
