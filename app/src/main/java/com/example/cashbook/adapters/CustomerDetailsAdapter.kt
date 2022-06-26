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
import com.example.cashbook.models.CustomerDetailsEntity
import com.example.cashbook.models.CustomerEntity

import java.util.ArrayList


class CustomerDetailsAdapter(private var listdata: ArrayList<CustomerDetailsEntity>) :
    RecyclerView.Adapter<CustomerDetailsAdapter.ViewHolder>(), Filterable {
    var customerList: ArrayList<CustomerDetailsEntity> = ArrayList()
    //var customerFilteredList: ArrayList<CustomerDetailsEntity> = ArrayList()
    init {
        customerList = listdata
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.customer_details_item, parent, false)
        return ViewHolder(listItem)
    }
    fun updateList(list: ArrayList<CustomerDetailsEntity>){
        //listdata.clear()
        listdata.addAll(list)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.dateTextView.setText(listdata[position].date.toString())
        holder.balTextView.setText(listdata[position].balAmount.toString())
        holder.amountGiveTextView.setText(listdata[position].amountGive.toString())
        holder.amountGotTextView.setText(listdata[position].amountGot.toString())

        // holder.time.setText(listdata[position].getTime())

       // holder.itemView.setOnClickListener { clickListener.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateTextView: TextView
        var balTextView: TextView
        var amountGiveTextView: TextView
        var amountGotTextView :TextView


        init {
            dateTextView = itemView.findViewById<View>(R.id.tv_date) as TextView
            balTextView = itemView.findViewById<View>(R.id.tv_amount) as TextView
            amountGiveTextView = itemView.findViewById<View>(R.id.tv_give) as TextView
            amountGotTextView = itemView.findViewById<View>(R.id.tv_got) as TextView

            //time = itemView.findViewById(R.id.call_time)

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) listdata = customerList else {
                    val filteredList = ArrayList<CustomerDetailsEntity>()
                    customerList
                        .filter {
                            (it.phoneNumber?.contains(constraint!!)!!)

                        }
                        .forEach { filteredList.add(it) }
                    listdata = filteredList

                }
                return FilterResults().apply { values = listdata }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                listdata = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<CustomerDetailsEntity>
                notifyDataSetChanged()
            }
        }
    }




    // RecyclerView recyclerView;

}
