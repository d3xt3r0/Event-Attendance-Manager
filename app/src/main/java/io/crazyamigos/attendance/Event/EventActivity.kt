package io.crazyamigos.attendance.Event

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import io.crazyamigos.attendance.LoginActivity
import io.crazyamigos.attendance.R
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.content_events.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        setSupportActionBar(toolbar)


        doAsync {

            val pref = getSharedPreferences("event", 0)
            val token = pref.getString("access_token", "")

            val request = Request.Builder()
                    .url("https://test3.htycoons.in/api/list_events")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Authorization", "Bearer $token")
                    .post(FormBody.Builder().build())
                    .build()

            val client = OkHttpClient()

            val response = client.newCall(request).execute()

            if (response.body() != null) {
                val responseString = response.body()!!.string()
                val json = JSONObject(responseString)

                val eventsJson = json.getJSONArray("events")

                val events = ArrayList<Event>()

                for (i in 0..eventsJson.length() - 1) {
                    val event = Event(
                            eventsJson.getJSONObject(i).getString("_id"),
                            eventsJson.getJSONObject(i).getString("event_name"),
                            eventsJson.getJSONObject(i).getString("date"),
                            eventsJson.getJSONObject(i).getString("venue"),
                            eventsJson.getJSONObject(i).getInt("days"),
                            eventsJson.getJSONObject(i).getInt("no_of_participants")
                    )

                    events.add(event)
                }

                uiThread {

                    recyclerView.layoutManager = LinearLayoutManager(this@EventActivity,
                            LinearLayoutManager.VERTICAL,
                            false)

                    val adapter = EventAdapter(events)

                    recyclerView.adapter = adapter
                }

            }
        }

        fab.setOnClickListener {
            startActivity(intentFor<CreateEventActivity>())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {

            R.id.logout -> {
                val pref = getSharedPreferences("event", 0)
                val editor = pref.edit()
                editor.putString("access_token", "")
                editor.apply()

                startActivity(intentFor<LoginActivity>())
                finish()
            }

            R.id.about -> {

            }
        }


        return super.onOptionsItemSelected(item)
    }


}
