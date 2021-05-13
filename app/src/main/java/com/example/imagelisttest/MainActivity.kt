package com.example.imagelisttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.imagelisttest.bean.PhotoListBean

class MainActivity : AppCompatActivity() {
    private lateinit var mRequestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRequestQueue = Volley.newRequestQueue(this)
        getImageData()
    }

    private fun getImageData(){
        val url = "https://api.unsplash.com/photos/?page=1&per_page=10&order_by=popular"
        var header = mutableMapOf("Authorization" to "Client-ID " + Config.API_KEY)

        val jsonObjectRequest = GsonRequest<PhotoListBean>(url,PhotoListBean::class.java,header,createMyReqSuccessListener(),createMyReqErrorListener())
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


    private fun createMyReqSuccessListener(): Response.Listener<PhotoListBean>{
        return Response.Listener {
            print(it)
        }
    }


    private fun createMyReqErrorListener(): Response.ErrorListener{
        return Response.ErrorListener {
            print(it)
        }
    }
}