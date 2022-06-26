package com.example.cashbook.adapters

import android.annotation.SuppressLint
import android.provider.Contacts
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashbook.R
import com.example.cashbook.clickListeners.CustomerAdapterClickListener
import com.example.cashbook.models.CustomerEntity
import java.util.ArrayList

class CustomerAdapter(private var listdata: ArrayList<CustomerEntity>,private val clickListener: CustomerAdapterClickListener) :
    RecyclerView.Adapter<CustomerAdapter.ViewHolder>(), Filterable {
    var customerList: ArrayList<CustomerEntity> = ArrayList()
    //var customerFilteredList: ArrayList<CustomerEntity> = ArrayList()
init {
    customerList = listdata
}
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.customer_item, parent, false)
        return ViewHolder(listItem)
    }
    fun updateList(list:ArrayList<CustomerEntity>){
        //listdata.clear()
        listdata.addAll(list)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.amountTextView.setText("Rs "+listdata[position].balAmount.toString())
        holder.nameTextView.setText(listdata[position].name)
        holder.getGiveTextView.setText(listdata[position].getGiveStatus)
       // holder.time.setText(listdata[position].getTime())

        holder.itemView.setOnClickListener { clickListener.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView
        var amountTextView: TextView
        var getGiveTextView: TextView


        init {
            nameTextView = itemView.findViewById<View>(R.id.person_name) as TextView
            amountTextView = itemView.findViewById<View>(R.id.tv_amount) as TextView
            getGiveTextView = itemView.findViewById<View>(R.id.tv_get_give) as TextView

            //time = itemView.findViewById(R.id.call_time)

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) listdata = customerList else {
                    val filteredList = ArrayList<CustomerEntity>()
                    customerList
                        .filter {
                            (it.name?.contains(constraint!!)!!)

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
                    results.values as ArrayList<CustomerEntity>
                notifyDataSetChanged()
            }
        }
    }




    // RecyclerView recyclerView;

}
