package com.example.cashbook.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashbook.R
import com.example.cashbook.clickListeners.CustomerAdapterClickListener
import com.example.cashbook.models.CustomerEntity
import com.example.cashbook.models.VehicleEntity
import java.util.ArrayList


class VehicleAdapter(private var listdata: ArrayList<VehicleEntity>, private val clickListener: CustomerAdapterClickListener) :
    RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {
    //var customerList: ArrayList<VehicleEntity> = ArrayList()
    //var customerFilteredList: ArrayList<VehicleEntity> = ArrayList()
    init {
       // customerList = listdata
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.vehicle_item, parent, false)
        return ViewHolder(listItem)
    }


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.buyPrice.setText(listdata[position].buyPrice.toString())
        holder.salePrice.setText(listdata[position].salePrice.toString())
        if(listdata[position].buyPrice.equals("")){
            holder.buyPrice.setBackgroundColor(R.color.light_red)
            }
        // holder.time.setText(listdata[position].getTime())

        holder.itemView.setOnClickListener { clickListener.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var buyPrice: TextView
        var salePrice: TextView
        var busNumber: TextView


        init {
            buyPrice = itemView.findViewById<View>(R.id.tv_buy_price) as TextView
            salePrice = itemView.findViewById<View>(R.id.tv_sale_price) as TextView
            busNumber = itemView.findViewById<View>(R.id.tv_vehicle_number) as TextView

            //time = itemView.findViewById(R.id.call_time)

        }
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charString = constraint?.toString() ?: ""
//                if (charString.isEmpty()) listdata = customerList else {
//                    val filteredList = ArrayList<VehicleEntity>()
//                    customerList
//                        .filter {
//                            (it.name?.contains(constraint!!)!!)
//
//                        }
//                        .forEach { filteredList.add(it) }
//                    listdata = filteredList
//
//                }
//                return FilterResults().apply { values = listdata }
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//
//                listdata = if (results?.values == null)
//                    ArrayList()
//                else
//                    results.values as ArrayList<VehicleEntity>
//                notifyDataSetChanged()
//            }
//        }
//    }




    // RecyclerView recyclerView;

}
