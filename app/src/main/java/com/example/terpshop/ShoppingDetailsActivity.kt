package com.example.terpshop

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class ShoppingDetailsActivity : AppCompatActivity() {
    private lateinit var continueBtn2: Button
    private lateinit var addItemBtn: Button
    private lateinit var categorySpinner: Spinner
    lateinit var itemName: EditText
    private lateinit var itemDetails: EditText
    private lateinit var relevance: RadioGroup
    private lateinit var tvAddedItems: TextView
    private val addedItemsList = mutableListOf<String>()

    val full_List_of_items = ArrayList<ItemData>()


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_data)



        tvAddedItems = findViewById(R.id.tvAddedItems)
        tvAddedItems.text = "Added Items:"
        continueBtn2 = findViewById(R.id.continueBtn2)
        addItemBtn = findViewById(R.id.btn_add_item)
        categorySpinner = findViewById(R.id.spinner_category)
        itemName = findViewById(R.id.edit_text_item_name)
        itemDetails = findViewById(R.id.edit_text_item_details)
        relevance = findViewById(R.id.radio_group_relevance)

        val clearCartBtn: Button = findViewById(R.id.clearCartBtn)
        clearCartBtn.setOnClickListener {
            clearCart()
            showToast("Cart is Empty")
        }


        // Load previously saved items for the current user from SharedPreferences
        loadSavedItemsForCurrentUser()


        loadSavedItemsForCurrentUser()

        val addedItemsFromIntent = intent.getStringArrayListExtra("itemNames")
        if (addedItemsFromIntent != null) {
            addedItemsList.clear()
            addedItemsList.addAll(addedItemsFromIntent)
            updateAddedItemsTextView()
        }


        val fullListFromIntent = intent.getSerializableExtra("FullList", ArrayList::class.java) as? ArrayList<ItemData>
        if (fullListFromIntent != null) {
            full_List_of_items.clear()
            full_List_of_items.addAll(fullListFromIntent)
        }


        // Define an array of shops
        val shops = arrayOf(
            "Select Category > ",
            "Food",
            "School Supplies",
            "Hygiene Products",
            "Electronics",
            "Clothing",
            "Books",
            "Household items",
            "Beauty Products",
            "Medications",
            "Sports Equipment",
            "Office Supplies",
            "Miscellaneous"
        ) // list of shops at UMD

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shops)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        categorySpinner.adapter = adapter

        addItemBtn.setOnClickListener {
            handleAddItemButtonClick()
        }

        continueBtn2.setOnClickListener {
            if (addedItemsList.isNotEmpty()) {

                // Save added items list in SharedPreferences
                saveAddedItemsListForCurrentUser()

                val intent = Intent(this, ContactInfoActivity::class.java)
                intent.putExtra("itemNames", addedItemsList as Serializable)
                intent.putExtra("FullList", full_List_of_items as Serializable)

                Log.w("MainActivity", "Sent from shopping activity to contact " + full_List_of_items)


                //SEND TO CONTACT

                startActivity(intent)
            } else {
                showToast("Please add at least one item to your cart")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, "$message", Toast.LENGTH_SHORT).show()
    }

    private fun handleAddItemButtonClick() {
        val selectedRadioButtonId = relevance.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedRadioButtonId)

        if (categorySpinner.selectedItemPosition <= 0) {
            showToast("Please select a category")
        } else if (itemName.text.isEmpty()) {
            showToast("Please enter an item name")
        } else if (itemDetails.text.isEmpty()) {
            showToast("Please enter item details")
        } else if (radioButton == null) {
            showToast("Please select relevance")
        } else {

            val name = itemName.text.toString()
            val category = categorySpinner.selectedItem.toString()
            val details = itemDetails.text.toString()
            val relevance = radioButton.text.toString()
            val data = ItemData(name, category, details, relevance)
            full_List_of_items.add(data)
            // Valid input, proceed to the next activity
            addedItemsList.add(itemName.text.toString())


            // Update the TextView to display added items
            updateAddedItemsTextView()

            // Clear input fields and radio button selection
            clearInputFields()
            hideSoftKeyboard()

            showToast("Item '${itemName.text}' added successfully!")
        }
    }

    private fun updateAddedItemsTextView() {
        // Update the TextView with the list of added items
        val cartText = "Items On Your Cart\n${buildAddedItemsText()}"
        tvAddedItems.text = cartText
        tvAddedItems.textSize = 20f // Set the desired text size here
    }

    private fun buildAddedItemsText(): String {
        return addedItemsList.joinToString("\n") { "- $it" }
    }


    private fun clearInputFields() {
        // Clear input fields and radio button selection
        categorySpinner.setSelection(0)
        itemName.text.clear()
        itemDetails.text.clear()
        relevance.clearCheck()
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun loadSavedItemsForCurrentUser() {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val currentUser = sharedPreferences.getString("currentUser", null)

        if (!currentUser.isNullOrEmpty()) {
            val savedItemsKey = "addedItemsList_$currentUser"
            val savedItems = sharedPreferences.getStringSet(savedItemsKey, null)
            if (savedItems != null) {
                addedItemsList.addAll(savedItems)
                updateAddedItemsTextView()
            }
        }
    }

    private fun saveAddedItemsListForCurrentUser() {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val currentUser = sharedPreferences.getString("currentUser", null)

        if (!currentUser.isNullOrEmpty()) {
            val editor = sharedPreferences.edit()
            val savedItemsKey = "addedItemsList_$currentUser"
            editor.putStringSet(savedItemsKey, addedItemsList.toSet())
            editor.apply()
        }
    }

    private fun clearCart() {
        addedItemsList.clear()
        full_List_of_items.clear()
        updateAddedItemsTextView()
    }
}