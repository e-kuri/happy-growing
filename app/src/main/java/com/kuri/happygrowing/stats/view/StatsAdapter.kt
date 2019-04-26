package com.kuri.happygrowing.stats.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuri.happygrowing.R
import com.kuri.happygrowing.stats.model.Measurement
import java.text.SimpleDateFormat

internal class StatsAdapter(private var statsSet: List<Measurement>) :
    RecyclerView.Adapter<StatsAdapter.StatsViewHolder>(){

    companion object{
        const val DATE_FORMAT = "dd/MM/yyyy HH:mm"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_stats, parent, false)
        return StatsViewHolder(view)
    }

    override fun getItemCount() = statsSet.size

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        holder.bindItem(statsSet[position])
    }

    fun updateValues(statsSet: List<Measurement>){
        this.statsSet = statsSet
        notifyDataSetChanged()
    }

    class StatsViewHolder(item: View) : RecyclerView.ViewHolder(item){
        private val mTitleTextView: TextView = item.findViewById(R.id.textview_title_stats)
        private val mDateTextView: TextView = item.findViewById(R.id.textview_date_stats)
        private val mValueTextView: TextView = item.findViewById(R.id.textview_value_stats)

        fun bindItem(measurement: Measurement){
            mTitleTextView.text = measurement.type.toString()
            mValueTextView.text = measurement.value.toString()
            mDateTextView.text = SimpleDateFormat(DATE_FORMAT).format(measurement.date)
        }
    }

}