package io.crazyamigos.attendance.dashboard

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.crazyamigos.attendance.R
import io.crazyamigos.attendance.dashboard.fragments.AddMemberFragment
import io.crazyamigos.attendance.dashboard.fragments.HomeFragment
import io.crazyamigos.attendance.dashboard.fragments.MemberListFragment
import io.crazyamigos.attendance.dashboard.fragments.QRScanFragment
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    val bundle = Bundle()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                val homeFragment = HomeFragment()
                homeFragment.arguments = bundle
                replaceFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add_member -> {
                val addMember = AddMemberFragment()
                replaceFragment(addMember)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_scan_qr -> {
                val qr = QRScanFragment()
                replaceFragment(qr)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_members_list -> {
                val memberList = MemberListFragment()
                replaceFragment(memberList)
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        bundle.putString("name", intent.getStringExtra("name") )
        bundle.putString("days", intent.getStringExtra("days") )
        bundle.putString("date", intent.getStringExtra("date") )
        bundle.putString("venue", intent.getStringExtra("venue") )

        val homeFragment = HomeFragment()

        homeFragment.arguments = bundle
        replaceFragment(homeFragment)


    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.base_layout, fragment)
                .commit()
    }
}
