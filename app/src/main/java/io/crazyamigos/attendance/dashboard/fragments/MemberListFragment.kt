package io.crazyamigos.attendance.dashboard.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.crazyamigos.attendance.Event.EventAdapter
import io.crazyamigos.attendance.R
import kotlinx.android.synthetic.main.add_member_fragment.*
import kotlinx.android.synthetic.main.content_events.*
import kotlinx.android.synthetic.main.member_list_fragment.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class MemberListFragment : Fragment()   {

    val names = ArrayList<String>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.member_list_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doAsync {

            val body = FormBody.Builder()
                    .add("event_id", arguments!!.getString("id"))
                    .build()

            val pref = context!!.getSharedPreferences("event", 0)
            val token = pref.getString("access_token", "")

            val request = Request.Builder()
                    .url("https://test3.htycoons.in/api/list_participants")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Authorization", "Bearer $token")
                    .post(body)
                    .build()

            val client = OkHttpClient()

            val response = client.newCall(request).execute()

            Log.d("TEQT", response.body()!!.string())

            if (response.body() != null){

                val responseString = response.body()!!.string()
                val json = JSONObject(responseString)
                val namesJson = json.getJSONArray("members")

                for(i in 0..namesJson.length() - 1){
                    names.add(namesJson.getJSONObject(i).getString("name"))
                }


            }


            uiThread {

                partRecyclerView.layoutManager = LinearLayoutManager(this@MemberListFragment.context,
                        LinearLayoutManager.VERTICAL,
                        false)

                val adapter = MemberListAdapter(names)

                recyclerView.adapter = adapter

            }

        }
    }
}