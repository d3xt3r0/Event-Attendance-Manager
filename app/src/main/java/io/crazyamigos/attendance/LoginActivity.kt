package io.crazyamigos.attendance

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.crazyamigos.attendance.Event.EventActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pref = getSharedPreferences("event", 0)
        val token = pref.getString("access_token", "")
        if (token != "") {
            startActivity(intentFor<EventActivity>())
            finish()
        }


    }


    fun doLogin(view: View) {
        if (username.text.toString().isEmpty() || password.text.toString().isEmpty())
            toast("Invalid username or password!!")
        else {
            login()
        }
    }


    fun login() {
        progress.visibility = View.VISIBLE

        doAsync {

            val body = FormBody.Builder()
                    .add("username", username.text.toString())
                    .add("password", password.text.toString())
                    .build()

            val request = Request.Builder()
                    .url("https://test3.htycoons.in/api/login")
                    .post(body)
                    .build()

            val client = OkHttpClient()

            val response = client.newCall(request).execute()


            uiThread {

                progress.visibility = View.INVISIBLE


                when (response.code()) {

                    200 -> {
                        if (response.body() != null) {
                            val jsonResponse = JSONObject(response.body()!!.string())
                            val accessToken = jsonResponse.getString("access_token")


                            val pref = getSharedPreferences("event", 0)
                            val editor = pref.edit()
                            editor.putString("access_token", accessToken)
                            editor.apply()

                            startActivity(intentFor<EventActivity>())
                            finish()

                        }

                    }

                    400 -> {
                        AlertDialog.Builder(this@LoginActivity)
                                .setTitle("Error")
                                .setMessage("An error has occured!")
                                .setNeutralButton("OK") {dialog, which ->
                                    dialog.dismiss()
                                }
                                .show()


                    }

                }

            }

        }


    }


}





















