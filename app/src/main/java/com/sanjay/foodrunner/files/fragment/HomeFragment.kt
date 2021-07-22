package com.sanjay.foodrunner.files.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.sanjay.foodrunner.R
import com.sanjay.foodrunner.files.adapter.HomeRecyclerAdapter
import com.sanjay.foodrunner.files.model.Restaurants
import com.sanjay.foodrunner.files.util.ConnectionManager
import org.json.JSONException


class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: HomeRecyclerAdapter

    lateinit var progressBar : ProgressBar
    lateinit var progressLayout : RelativeLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val restaurantInfoList= arrayListOf<Restaurants>(

        )

        progressBar = view.findViewById(R.id.progressBar)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        recyclerView = view.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(activity)

        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        if (ConnectionManager().checkConnectivity(activity as Context)) {

            val jsonObjectRequest =  object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {

                try {

                    progressLayout.visibility = View.GONE
                    println("Response $it")
                    val info = it.getJSONObject("data")
                    val success = info.getBoolean("success")
                    if (success) {

                        val data = info.getJSONArray("data")
                        for (i in 0 until data.length()) {

                            val jsonObject = data.getJSONObject(i)
                            val restObject = Restaurants(

                                jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("rating"),
                                jsonObject.getString("cost_for_one"),
                                jsonObject.getString("image_url")
                            )
                            restaurantInfoList.add(restObject)

                            recyclerAdapter = HomeRecyclerAdapter(activity as Context,restaurantInfoList)

                            recyclerView.adapter = recyclerAdapter
                            recyclerView.layoutManager = layoutManager
                        }

                    }
                    else {
                        Toast.makeText(activity as Context,"Some Error",Toast.LENGTH_SHORT).show()
                    }

                }catch (e:JSONException) {
                    Toast.makeText(activity as Context,"Json Execptiom",Toast.LENGTH_SHORT).show()
                }


            },
            Response.ErrorListener {

                println{"Error $it"}
                Toast.makeText(activity as Context,"Volley Error",Toast.LENGTH_SHORT).show()

            })
            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "d0076af4b6ca9b"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }

        

        return view
    }
}
