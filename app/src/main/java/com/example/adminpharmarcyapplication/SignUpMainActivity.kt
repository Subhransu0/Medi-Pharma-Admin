package com.example.adminpharmarcyapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminpharmarcyapplication.databinding.ActivitySignUpMainBinding
import com.example.adminpharmarcyapplication.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpMainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emaill: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var nameOfMedicineStore: String
    private lateinit var database: DatabaseReference


    private val binding: ActivitySignUpMainBinding by lazy {
        ActivitySignUpMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        // initialize Firebase Auth

        auth = Firebase.auth

        database = Firebase.database.reference





        binding.createUserBtn.setOnClickListener {

            emaill = binding.signUpemail.text.toString().trim()
            userName = binding.ownername.text.toString().trim()
            nameOfMedicineStore = binding.medicineStoreName.text.toString().trim()
            password = binding.signUppasswword.text.toString().trim()

            if (userName.isBlank() || emaill.isBlank() || nameOfMedicineStore.isBlank() || password.isBlank()
            ){
                Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show()
            }else{
                createAccount(emaill,password)
            }



        }
        binding.AlreadyHaveButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val locatinList = arrayOf(
            "Odisha",
            "Mumbai",
            "Delhi",
            "Raipur",
            "Berhempur",
            "Chandigarh",
            "Noida",
            "Bhubaneswar",
            "Ahemdabad"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locatinList)
        val autocompletetextview = binding.listOfLocation
        autocompletetextview.setAdapter(adapter)
    }

    private fun createAccount(emaill: String, password: String) {
         auth.createUserWithEmailAndPassword(emaill,password).addOnCompleteListener{task ->
             if(task.isSuccessful){
                 Toast.makeText(this,"Account Create Successfully",Toast.LENGTH_SHORT).show()
                 saveUserData()
                 val intent = Intent(this, LoginActivity::class.java)
                 startActivity(intent)
                 finish()
             }
             else{
                 Toast.makeText(this,"Account creation Failed !!!",Toast.LENGTH_SHORT).show()
                 Log.e("Account", "createAccount: Failuer",task.exception )
             }
         }
    }

    private fun saveUserData() {
        emaill = binding.signUpemail.text.toString().trim()
        userName = binding.ownername.text.toString().trim()
        nameOfMedicineStore = binding.medicineStoreName.text.toString().trim()
        password = binding.signUppasswword.text.toString().trim()
        val user = UserModel(userName,nameOfMedicineStore,emaill,password)
        val userId  =FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}