package io.crazyamigos.attendance.dashboard.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.crazyamigos.attendance.Event.EventActivity
import io.crazyamigos.attendance.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.add_member_fragment.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class AddMemberFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_member_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_cont_btn.setOnClickListener {

            doAsync {

                val body = FormBody.Builder()
                        .add("event_id", "rtrtrtrtrtrt")
                        .add("name", cont_name.text.toString())
                        .add("phone_number", cont_phone.text.toString())
                        .add("email_address", cont_email.text.toString())
                        .add("organization", "simat")
                        .add("reg_id", "skdh")
                        .build()

                val pref = context!!.getSharedPreferences("event", 0)
                val token = pref.getString("access_token", "")

                val request = Request.Builder()
                        .url("https://test3.htycoons.in/api/add_participant")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("Authorization", "Bearer $token")
                        .post(body)
                        .build()

                val client = OkHttpClient()

                val response = client.newCall(request).execute()

                Log.d("TEQT", response.body()!!.string())


                uiThread {


                    when (response.code()) {

                        200 -> {
                            if (response.body() != null) {
                                /*val jsonResponse = JSONObject(response.body()!!.string())
                                val status = jsonResponse.getString("message")*/
                                Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
                            }

                        }

                        400 -> {
                            Toast.makeText(context, "not working", Toast.LENGTH_SHORT).show()
                            cont_name.setText("not working")

                        }

                    }

                }

            }

        }
    }

}