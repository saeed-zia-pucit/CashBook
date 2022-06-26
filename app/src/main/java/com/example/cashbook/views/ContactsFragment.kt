package com.example.cashbook.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashbook.R
import com.example.cashbook.adapters.CustomerAdapter
import com.example.cashbook.clickListeners.CustomerAdapterClickListener
import com.example.cashbook.database.DatabaseBuilder
import com.example.cashbook.database.DatabaseHelperImpl
import com.example.cashbook.models.CustomerEntity
import com.example.cashbook.utils.AppConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContactsFragment : Fragment(), CustomerAdapterClickListener,View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var recyclerView: RecyclerView? = null
    var adapter: CustomerAdapter? = null
    var customerList = ArrayList<CustomerEntity>()
    val PICK_CONTACT = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(view)
        setListeners()
        getCustomersFromDb();
    }
    fun setListeners(){
        extended_fab.setOnClickListener(this);
        //search_view.setOnQueryTextListener(this);
    }
    fun setRecyclerView(view: View) {

        adapter = CustomerAdapter(customerList,this)
        recyclerView =view.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView!!.setLayoutManager(LinearLayoutManager(requireContext()))
        recyclerView!!.setAdapter(adapter)
    }

    override fun onItemClick(index: Int) {
        val intent = Intent(requireContext(),CustomerDetailsActivity::class.java)
        intent.putExtra(AppConstants.SELECTED_OBJECT,customerList.get(index))
        startActivity(intent)
    }
    fun getCustomersFromDb() {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))

        GlobalScope.launch {

            var  customerList1 = dbHelper.getUsers() as ArrayList<CustomerEntity>
            activity?.runOnUiThread {
                customerList.addAll(customerList1)
                // adapter?.updateList(customerList)
                adapter?.notifyDataSetChanged()

            }
        }

    }
    fun saveContactsToDb(contactEntity: CustomerEntity) {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))

        GlobalScope.launch {
            dbHelper.insert(contactEntity)

        }

    }
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.extended_fab -> {

                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}