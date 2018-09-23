package com.kushpatel.gettogether.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kushpatel.gettogether.EventActivity
import com.kushpatel.gettogether.R
import kotlinx.android.synthetic.main.sports_row.view.*
import com.kushpatel.gettogether.models.Sport

//import kotlinx.android.synthetic.main.sports_row.view.*

class SportsAdapter(val list: ArrayList<Sport>): RecyclerView.Adapter<SportsViewHolder>() {

    private val SPORTS_TAG = "SportsActivity"


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.sports_row, parent,false)
        view.setOnClickListener {
            Log.d(SPORTS_TAG,"It: $it")
        }
        return SportsViewHolder(view)
    }


    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {
        var item: Sport = list.get(position)
        holder?.sportsName?.text = item.name
    }



}

class SportsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val SPORTS_TAG = "SportsActivity"

    init {
        view.setOnClickListener {
            var intent = Intent(view.context, EventActivity::class.java)
            view.context.startActivity(intent)
            Log.d(SPORTS_TAG,"$it")

        }
    }
    val sportsName = view.sportsName

}

