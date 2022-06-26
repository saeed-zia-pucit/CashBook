package com.example.cashbook.views

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashbook.R
import com.example.cashbook.adapters.CustomerAdapter
import com.example.cashbook.clickListeners.CustomerAdapterClickListener
import com.example.cashbook.models.CustomerEntity
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.ContactsContract

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cashbook.database.DatabaseBuilder
import com.example.cashbook.database.DatabaseHelperImpl
import com.example.cashbook.utils.AppConstants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(),CustomerAdapterClickListener,View.OnClickListener,SearchView.OnQueryTextListener {
    val PERMISSIONS_REQUEST_READ_CONTACTS = 2

    var recyclerView: RecyclerView? = null
    var adapter: CustomerAdapter? = null
    var customerList = ArrayList<CustomerEntity>()
    val PICK_CONTACT = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView()
        setListeners()
        getCustomersFromDb();
    }
    fun setListeners(){
        extended_fab.setOnClickListener(this);
        search_view.setOnQueryTextListener(this);
    }
    fun setRecyclerView() {

        adapter = CustomerAdapter(customerList,this)
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        recyclerView!!.setAdapter(adapter)
    }

    override fun onItemClick(index: Int) {
        val intent = Intent(this,CustomerDetailsActivity::class.java)
        intent.putExtra(AppConstants.SELECTED_OBJECT,customerList.get(index))
        startActivity(intent)
    }
    fun pickContact(){
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, PICK_CONTACT)
    }
    fun getCustomersFromDb() {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))

        GlobalScope.launch {

           var  customerList1 = dbHelper.getUsers() as ArrayList<CustomerEntity>
            runOnUiThread {
                customerList.addAll(customerList1)
               // adapter?.updateList(customerList)
                adapter?.notifyDataSetChanged()

            }
        }

    }
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.extended_fab -> {
                    requestContactPermission()
                }
            }
        }
    }

    //code
    @SuppressLint("Range")
    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        var  cNumber=""
        super.onActivityResult(reqCode, resultCode, data)
        when (reqCode) {
            PICK_CONTACT -> if (resultCode == RESULT_OK) {
                val contactData: Uri? = data?.data
                val c: Cursor = managedQuery(contactData, null, null, null, null)
                if (c.moveToFirst()) {
                    val id: String =
                        c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                    val hasPhone: String =
                        c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    if (hasPhone.equals("1", ignoreCase = true)) {
                        val phones: Cursor? = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null
                        )
                        if (phones != null) {
                            phones.moveToFirst()
                        }
                        if (phones != null) {
                             cNumber = phones.getString(phones.getColumnIndex("data1"))
                        }
                    }
                    val name: String =
                        c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                      val temp = CustomerEntity(name,cNumber,0,"")
                      customerList.add(temp)
                    //adapter?.updateList(customerList)
                    adapter?.notifyDataSetChanged()
                    saveContactsToDb(temp)
                }
            }
        }
    }
    fun saveContactsToDb(contactEntity: CustomerEntity) {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))

        GlobalScope.launch {
            dbHelper.insert(contactEntity)

        }

    }
    fun requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_CONTACTS)) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Read Contacts permission")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage("Please enable access to contacts.")
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.READ_CALL_LOG),
                            PERMISSIONS_REQUEST_READ_CONTACTS)
                    }
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG),
                        PERMISSIONS_REQUEST_READ_CONTACTS)
                }
            } else {
                pickContact()
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED )
                {
                   pickContact()
                } else {
                    Toast.makeText(this,"You have disabled a contacts permission", Toast.LENGTH_LONG).show()
                }
                return
            }

        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter?.getFilter()?.filter(newText)
        return false
    }
}