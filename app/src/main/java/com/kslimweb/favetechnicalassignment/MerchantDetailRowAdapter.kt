package com.kslimweb.favetechnicalassignment

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.merchant_detail_row.view.*

class MerchantDetailRowAdapter(val merchantDetailList: MutableList<MerchantDetail>) : RecyclerView.Adapter<CustomViewHolder>() {

    private val TAG = this::class.java.simpleName

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val merchantName = merchantDetailList[position].companyName

        val listOfLocation = merchantDetailList[position].location

        var locationsText = ""
        for (location in listOfLocation) {
            if(location != "") {
                locationsText = locationsText + "-   " + location + "\n"
            }
        }
        holder.view.merchant_name.text = merchantName
        holder.view.location.text = locationsText
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.merchant_detail_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return merchantDetailList.size
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view)