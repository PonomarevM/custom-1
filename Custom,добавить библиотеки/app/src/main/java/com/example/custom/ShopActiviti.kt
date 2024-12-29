package com.example.custom

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide


class ShopActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var productNameEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var productImageView: ImageView
    private lateinit var selectImageButton: Button
    private lateinit var addButton: Button
    private lateinit var productListView: ListView
    private var selectedImageUri: Uri? = null
    private val productList = mutableListOf<Product>()
    private lateinit var productAdapter: ProductAdapter
    private val PICK_IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        productNameEditText = findViewById(R.id.productNameEditText)
        productPriceEditText = findViewById(R.id.productPriceEditText)
        productImageView = findViewById(R.id.productImageView)
        selectImageButton = findViewById(R.id.selectImageButton)
        addButton = findViewById(R.id.addButton)
        productListView = findViewById(R.id.productList)
        productAdapter = ProductAdapter(this,productList)
        productListView.adapter = productAdapter

        selectImageButton.setOnClickListener {
            openGallery()
        }
        addButton.setOnClickListener {
            addProduct()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            Glide.with(this)
                .load(selectedImageUri)
                .into(productImageView)
        }
    }


    private fun addProduct(){
        val name = productNameEditText.text.toString()
        val price = productPriceEditText.text.toString()

        if(name.isNotEmpty() && price.isNotEmpty() ){
            val newProduct = Product(name, price, selectedImageUri)
            productList.add(newProduct)
            productAdapter.notifyDataSetChanged()
            productNameEditText.text.clear()
            productPriceEditText.text.clear()
            selectedImageUri = null
            productImageView.setImageDrawable(null)
        }
    }


}