package com.example.adminpharmarcyapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpharmarcyapplication.adapter.PendingOrderAdapter

import com.example.adminpharmarcyapplication.databinding.ActivityPendingOrderBinding


class PendingOrderActivity : AppCompatActivity() {
   private lateinit var binding : ActivityPendingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener {
            finish()
        }
        val OrderCustomerName = arrayListOf(
            "Bhuban Bam",
            "Naman Mathur",
            "John doe"
        )
        val OrderQuantity = arrayListOf(
            "8"
            ,"5",
            "3"
        )
        val orderedMedicineImage = arrayListOf(R.drawable.honey,R.drawable.medicine,R.drawable.pop_med4)
        val adapter = PendingOrderAdapter(OrderCustomerName, OrderQuantity, orderedMedicineImage,this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}