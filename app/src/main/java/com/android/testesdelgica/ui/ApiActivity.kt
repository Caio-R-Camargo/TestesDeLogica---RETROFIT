package com.android.testesdelgica.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.testesdelgica.*
import com.android.testesdelgica.databinding.ActivityApiBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class ApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiBinding

    lateinit var adapter: Adapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerviewUser.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerviewUser.layoutManager = linearLayoutManager
        getData()
    }


    private fun getData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val responseBody = response.body()!!

                adapter = Adapter(baseContext, responseBody)
                adapter.notifyDataSetChanged()
                binding.recyclerviewUser.adapter = adapter
                binding.progressBar.visibility = View.GONE

            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                d("ApiActivity", "onFailure"+t.message)
                binding.offlineIcon.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                Snackbar.make(findViewById(android.R.id.content), t.message.toString(),
                    Snackbar.LENGTH_INDEFINITE).setAction("Action", null).setTextColor(
                    resources.getColor(R.color.white)).setAction("Ok", View.OnClickListener {}).setActionTextColor(
                    resources.getColor(R.color.white)).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.action_settings_day){
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}