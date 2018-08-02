package io.crazyamigos.attendance.dashboard.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.crazyamigos.attendance.R
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name.text = arguments!!.getString("name")
        date.text = arguments!!.getString("date")
        venue.text = arguments!!.getString("venue")
        days.text = arguments!!.getInt("days").toString()
    }


}