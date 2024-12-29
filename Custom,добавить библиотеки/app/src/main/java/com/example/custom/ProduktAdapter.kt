package com.example.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ProductAdapter(context: Context, private val products: List<Product>) :
    ArrayAdapter<Product>(context, 0, products) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_product, parent, false)
        val product = getItem(position)!!
        val imageView = view.findViewById<ImageView>(R.id.productImageView)
        val nameTextView = view.findViewById<TextView>(R.id.productNameTextView)
        val priceTextView = view.findViewById<TextView>(R.id.productPriceTextView)

        product.imageUri?.let {
            Glide.with(context)
                .load(it)
                .into(imageView)
        }

        nameTextView.text = product.name
        priceTextView.text = product.price
        return view
    }
}