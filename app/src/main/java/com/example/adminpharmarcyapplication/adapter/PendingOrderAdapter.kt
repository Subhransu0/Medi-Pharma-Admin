package com.example.adminpharmarcyapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpharmarcyapplication.databinding.PendingOrdeItemBinding

class PendingOrderAdapter(private val customerNames:ArrayList<String>,private val quantity:ArrayList<String> , private val medicineImages:ArrayList<Int>,private val context:Context) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingOrderAdapter.PendingOrderViewHolder {
        val binding = PendingOrdeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PendingOrderAdapter.PendingOrderViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = customerNames.size
 inner class PendingOrderViewHolder(private val binding :PendingOrdeItemBinding):RecyclerView.ViewHolder(binding.root) {
    private var isAccepted = false
     fun bind(position: Int) {
      binding.apply {
          CustomerName.text=customerNames[position]
          quantityty.text = quantity[position]
          medicineImage.setImageResource(medicineImages[position])
          Accept.apply {
              if(!isAccepted){
                  text = "Accept"
              }
              else{
                  text="Dispatch"
              }
              setOnClickListener{
                  if(!isAccepted){
                      text="Dispatch"
                      isAccepted = true
                      showToast("Order is Accepted")
                  }
                  else{
                      customerNames.removeAt(adapterPosition)
                      notifyItemRemoved(adapterPosition)
                      showToast("Order is Dispatched")
                  }
              }
          }
      }

     }
  private   fun showToast(message: String){
         Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
     }
 }
}