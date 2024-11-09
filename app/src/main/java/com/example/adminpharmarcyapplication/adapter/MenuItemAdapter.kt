package com.example.adminpharmarcyapplication.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminpharmarcyapplication.databinding.ItemItemBinding
import com.example.adminpharmarcyapplication.model.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference
) : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

    private val itemQuantites = IntArray(menuList.size) { 1 }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size

    inner class AddItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {

                val quantity = itemQuantites[position]
                val menuItem = menuList[position]
                val uriString = menuItem.medicineImage
                val uri =Uri.parse(uriString)


                medicinename.text = menuItem.medicineName
                medicinePrice.text = menuItem.medicinePrice

                Glide.with(context).load(uri).into(MedicineImage)

                quantityyyy.text = itemQuantites[position].toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }

                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }

                deleteButton.setOnClickListener {
                    deleteItem(position)
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantites[position] < 10) {
                itemQuantites[position]++
                binding.quantityyyy.text = itemQuantites[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantites[position] > 1) { // Ensure quantity doesn't go below 1
                itemQuantites[position]--
                binding.quantityyyy.text = itemQuantites[position].toString()
            }
        }

        private fun deleteItem(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size) // Adjusts the list after removal
        }
    }
}
