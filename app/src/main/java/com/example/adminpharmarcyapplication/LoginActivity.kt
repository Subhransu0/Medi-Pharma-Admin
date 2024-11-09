package com.example.adminpharmarcyapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.adminpharmarcyapplication.databinding.ActivityLoginBinding
import com.example.adminpharmarcyapplication.model.UserModel

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class LoginActivity : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private  var userName: String ?= null
    private  var nameOfMedicineStore: String ?= null




    private val binding : ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestIdToken(getString((R.string.gcm_defaultSenderId))).requestEmail().build()
        auth = Firebase.auth

        database = Firebase.database.reference




        binding.loginButton.setOnClickListener{

            email = binding.emailLogin.text.toString().trim()
            password = binding.passwwordLogin.text.toString().trim()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please filled email and password",Toast.LENGTH_SHORT).show()
            }
            else{
                createUserAccount(email,password)
            }


        }


        binding.DontHaveButton.setOnClickListener{
            val intent = Intent(this,SignUpMainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createUserAccount(email: String, password: String) {
auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task ->
    if(task.isSuccessful){
        val user = auth.currentUser
        Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show()
        updateUi(user)
    }
    else{
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
            if(task.isSuccessful){
                val user = auth.currentUser
                Toast.makeText(this, "Create user & Login Successfull", Toast.LENGTH_SHORT).show()
                saveUserData()
                updateUi(user)
            }
            else{
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createUserAccount: Authentication Failed",task.exception)
            }
        }
    }

}
    }

    private fun saveUserData() {
        email = binding.emailLogin.text.toString().trim()
        password = binding.passwwordLogin.text.toString().trim()
        val user = UserModel(userName,nameOfMedicineStore,email,password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }
    }

    private fun updateUi(user: FirebaseUser?) {
    startActivity(Intent(this,MainActivity::class.java))
    }



}