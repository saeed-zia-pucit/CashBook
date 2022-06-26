package com.example.cashbook.views

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashbook.R
import com.example.cashbook.adapters.VehicleAdapter
import com.example.cashbook.clickListeners.CustomerAdapterClickListener
import com.example.cashbook.database.DatabaseBuilder
import com.example.cashbook.database.DatabaseHelperImpl
import com.example.cashbook.models.VehicleEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VehicleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VehicleFragment : Fragment(),CustomerAdapterClickListener,View.OnClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter:VehicleAdapter
    private  var vehicleList:ArrayList<VehicleEntity>
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
init {
  vehicleList = arrayListOf()
}


    fun getVehiclessFromDb() {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))

        GlobalScope.launch {

           var temp = dbHelper.getAllVehicles() as ArrayList<VehicleEntity>
            activity?.runOnUiThread {
                vehicleList.clear()
                vehicleList.addAll(temp)
                adapter.notifyDataSetChanged()

            }
        }

    }
    fun setListeners(){
        extended_fab.setOnClickListener(this);
       // search_view.setOnQueryTextListener(this);
    }
    fun saveVehiclesToDb(vehicleEntity: VehicleEntity) {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))

        GlobalScope.launch {
            dbHelper.insert(vehicleEntity)

        }

    }
    fun updateVehicleInDb(vehicleEntity: VehicleEntity) {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))

        GlobalScope.launch {
            dbHelper.update(vehicleEntity)

        }

    }
    fun setRecyclerView(view: View) {

        adapter = VehicleAdapter (vehicleList,this)
        recyclerView =view.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView

        recyclerView!!.setAdapter(adapter)
        recyclerView!!.setLayoutManager(LinearLayoutManager(context))
    }
    private fun showCustomDialog(index: Int=0) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        val phone = dialog.findViewById(R.id.et_phone) as EditText
        val name = dialog.findViewById(R.id.et_name) as EditText
        val busNumber = dialog.findViewById(R.id.et_bus) as EditText
        val note = dialog.findViewById(R.id.et_note) as EditText
        val buyPrice = dialog.findViewById(R.id.et_buy_prie) as EditText
        val salePrice = dialog.findViewById(R.id.et_sale_prie) as EditText


//        phone.setText(finalList.get(index).number)
//        name.setText(finalList.get(index).name)
//        busNumber.setText(finalList.get(index).busNumber)
//        note.setText(finalList.get(index).note)
        val yesBtn = dialog.findViewById(R.id.btn_save) as Button
        val noBtn = dialog.findViewById(R.id.btn_cancel) as Button
//        val temp= finalList.get(index)
        yesBtn.setOnClickListener {
//
//            finalList.set(index,ContactEntity(name.text.toString(),phone.text.toString(),temp.duration,temp.time,busNumber.text.toString(),note.text.toString(),Integer.parseInt(rate.text.toString()),temp.callType))
//            saveContactsToDb(ContactEntity(name.text.toString(),phone.text.toString(),temp.duration,temp.time,busNumber.text.toString(),note.text.toString(),Integer.parseInt(rate.text.toString()),temp.callType))
//            adapter?.notifyDataSetChanged()
           var v= VehicleEntity(busNumber.text.toString(),name.text.toString(),buyPrice.text.toString(),salePrice.text.toString(),"","","")
           vehicleList.add(v);

            adapter.notifyDataSetChanged()
            updateVehicleInDb(v)
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        getVehiclessFromDb()
        setRecyclerView(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vehicle, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VehicleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(index: Int) {
     showCustomDialog(index)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
              R.id.extended_fab->
              {
                  showCustomDialog()
              }
            }
        }

    }
}