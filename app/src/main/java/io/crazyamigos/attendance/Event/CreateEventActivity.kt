package io.crazyamigos.attendance.Event

import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import io.crazyamigos.attendance.R
import kotlinx.android.synthetic.main.add_member_fragment.*
import kotlinx.android.synthetic.main.create_event.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CreateEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_event)

        event_add_button.setOnClickListener {

            createEventProgress.visibility = View.VISIBLE

            doAsync {

                val body = FormBody.Builder()
                        .add("event_name", event_name.text.toString())
                        .add("days", days.text.toString())
                        .add("no_of_participants", no_of_part.text.toString())
                        .add("date", date.text.toString())
                        .add("venue", venue.text.toString())
                        .build()

                val pref = getSharedPreferences("event", 0)
                val token = pref.getString("access_token", "")
                Log.d("TEQT",token)

                val request = Request.Builder()
                        .url("https://test3.htycoons.in/api/create_event")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("Authorization", "Bearer $token")
                        .post(body)
                        .build()

                val client = OkHttpClient()

                val response = client.newCall(request).execute()

                //  Log.d("TEQT", response.body()!!.string())


                uiThread {


                    when (response.code()) {

                        200 -> {
                            if (response.body() != null) {
                                /*val jsonResponse = JSONObject(response.body()!!.string())
                                val status = jsonResponse.getString("message")*/
                                toast("Done")
                                createEventProgress.visibility = View.INVISIBLE
                                event_name.setText("")
                                days.setText("")
                                date.setText("")
                                no_of_part.setText("")
                                venue.setText("")
                            }

                        }

                        400 -> {
                            toast("Not working")
                            createEventProgress.visibility = View.INVISIBLE

                        }

                    }

                }

            }

        }
    }
}
