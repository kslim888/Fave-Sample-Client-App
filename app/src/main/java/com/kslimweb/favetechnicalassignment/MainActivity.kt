package com.kslimweb.favetechnicalassignment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.reflect.TypeToken



class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val PREFERENCE_NAME = "JSON_OBJECT"
        private const val PREFERENCE_JSON_OBJECT = "JSON_EDIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        merchantDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        val sharedPreferences = this.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        fetchJson("", sharedPreferences)

        searchMerchantName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val merchantName = editable.toString()
                Log.d(TAG, "Search Merchant Name: " + merchantName)
                fetchMerchantsDetails(merchantName)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    private fun fetchMerchantsDetails(searchMerchantName: String) {
        // get stored json from Shared Preference
        val merchantDetailList = getMerchantDetailList()

        val mutableMerchantDetailList: MutableList<MerchantDetail> = mutableListOf()

        for (merchantDetail in merchantDetailList!!) {
            if(merchantDetail.companyName.contains(searchMerchantName, ignoreCase = true)) {
                Log.d(TAG, "Searched company name true: " + merchantDetail.companyName)
                mutableMerchantDetailList.add(merchantDetail)
            }
        }

        runOnUiThread {
            merchantDetailRecyclerView.adapter = MerchantDetailRowAdapter(mutableMerchantDetailList)
        }
    }

    private fun fetchJson(merchantName: String, sharedPreferences: SharedPreferences) {
        val retrofit = RetrofitClient.retrofitInstance?.create(ApiService::class.java)

        retrofit?.getMerchantsDetails(merchantName)?.enqueue(object: Callback<List<MerchantDetail>> {
            override fun onFailure(call: Call<List<MerchantDetail>>, t: Throwable) {
                Log.d(TAG, t.message)
            }

            override fun onResponse(call: Call<List<MerchantDetail>>, response: Response<List<MerchantDetail>>) {

                val merchantDetailList = response.body()
                Log.d(TAG, "Response: " + merchantDetailList.toString())

                // save the json into Shared Preference
                val editor = sharedPreferences.edit()
                editor.putString(PREFERENCE_JSON_OBJECT, Gson().toJson(merchantDetailList))
                editor.apply()

                merchantDetailRecyclerView.adapter = MerchantDetailRowAdapter(merchantDetailList!!.toMutableList())

                runOnUiThread {
                    merchantDetailRecyclerView.adapter = MerchantDetailRowAdapter(merchantDetailList!!.toMutableList())
                }

            }
        })
    }

    private fun getMerchantDetailList(): List<MerchantDetail>? {

        val loadSharedPreferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val json = loadSharedPreferences.getString(PREFERENCE_JSON_OBJECT, null)
        val itemType = object : TypeToken<List<MerchantDetail>>() {}.type
        val merchantDetailList = Gson().fromJson<List<MerchantDetail>>(json, itemType)

        return merchantDetailList
    }
}
