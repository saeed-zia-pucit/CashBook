package com.example.cashbook.views

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashbook.R
import com.example.cashbook.adapters.CustomerAdapter
import com.example.cashbook.adapters.CustomerDetailsAdapter
import com.example.cashbook.database.DatabaseBuilder
import com.example.cashbook.database.DatabaseHelperImpl
import com.example.cashbook.models.CustomerDetailsEntity
import com.example.cashbook.models.CustomerEntity
import com.example.cashbook.utils.AppConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomerDetailsActivity : AppCompatActivity() {
  lateinit var selectedObject:CustomerEntity


    private var recyclerView: RecyclerView? = null
    var adapter: CustomerDetailsAdapter? = null
    var customerList = ArrayList<CustomerDetailsEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details)
        receiveObject()
        setRecyclerView()
        getCustomersFromDb()
    }
    private fun receiveObject(){
        selectedObject = intent.getSerializableExtra(AppConstants.SELECTED_OBJECT) as  CustomerEntity
    }
    fun setListeners(){

        //search_view.setOnQueryTextListener(this);
    }

    private fun setRecyclerView() {

        adapter = CustomerDetailsAdapter(customerList)
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        recyclerView!!.setAdapter(adapter)
    }
    fun getCustomersFromDb() {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))

        GlobalScope.launch {

            var  customerList1 = dbHelper.getUsers() as ArrayList<CustomerDetailsEntity>
            runOnUiThread {
                customerList.addAll(customerList1)
                // adapter?.updateList(customerList)
                adapter?.notifyDataSetChanged()

            }
        }

    }
    fun saveContactsToDb(contactEntity: CustomerDetailsEntity) {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))

        GlobalScope.launch {
           dbHelper.insert(contactEntity)

        }

    }
//    private fun showCustomDialog(index: Int) {
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.custom_dialog)
//        val phone = dialog.findViewById(R.id.et_phone) as EditText
//        val name = dialog.findViewById(R.id.et_name) as EditText
//        val busNumber = dialog.findViewById(R.id.et_bus) as EditText
//        val sale = dialog.findViewById(R.id.et_sale) as EditText
//        val note = dialog.findViewById(R.id.et_note) as EditText
//
//
//        phone.setText(customerList.get(index).phoneNumber)
//        busNumber.setText(customerList.get(index).truckNumber)
//        sale.setText(customerList.get(index).sa.toString())
//        buy.setText(finalList.get(index).note)
//        val yesBtn = dialog.findViewById(R.id.btn_save) as Button
//        val noBtn = dialog.findViewById(R.id.btn_cancel) as Button
//        val temp= finalList.get(index)
//        yesBtn.setOnClickListener {
//
//            finalList.set(index,ContactEntity(name.text.toString(),phone.text.toString(),temp.duration,temp.time,busNumber.text.toString(),note.text.toString(),Integer.parseInt(rate.text.toString()),temp.callType))
//            saveContactsToDb(ContactEntity(name.text.toString(),phone.text.toString(),temp.duration,temp.time,busNumber.text.toString(),note.text.toString(),Integer.parseInt(rate.text.toString()),temp.callType))
//            adapter?.notifyDataSetChanged()
//
//            dialog.dismiss()
//        }
//        noBtn.setOnClickListener { dialog.dismiss() }
//        dialog.show()
//
//    }
}