package com.example.adminpharmarcyapplication

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpharmarcyapplication.databinding.ActivityAddItemBinding
import com.example.adminpharmarcyapplication.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    private lateinit var medicineName: String
    private lateinit var medicinePrice: String
    private lateinit var medicineDescription: String
    private lateinit var medicinengridient: String
    private var medicineImageUri: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.addItemBtnnn.setOnClickListener {
            medicineName = binding.medicineName.text.toString().trim()
            medicinePrice = binding.medicinePrice.text.toString().trim()
            medicineDescription = binding.description.text.toString().trim()
            medicinengridient = binding.ingredient.text.toString().trim()

            if (medicineName.isNotBlank() && medicinePrice.isNotBlank() &&
                medicineDescription.isNotBlank() && medicinengridient.isNotBlank()
            ) {
                if (medicineImageUri != null) {
                    uploadData()
                } else {
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.selectImageTextView.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {
        val menuRef = database.getReference("menu")
        val newItemKey = menuRef.push().key

        if (medicineImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(medicineImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    val newItem = AllMenu(
                        medicineName = medicineName,
                        medicinePrice = medicinePrice,
                        medicineDescription = medicineDescription,
                        medicinengridient = medicinengridient,
                        medicineImage = downloadUrl.toString()
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Item uploaded successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Failed to upload item to database", Toast.LENGTH_SHORT).show()
                            }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedimageImagevIew.setImageURI(uri)
            medicineImageUri = uri
        }
    }
}
