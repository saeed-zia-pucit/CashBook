package com.example.cashbook.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cashbook.views.ContactsFragment
import com.example.cashbook.views.VehicleFragment


class ViewPagerAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        var fragment= VehicleFragment()
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return VehicleFragment()
            }
            1 -> {
                return ContactsFragment()
            }

            else -> return fragment
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}