package com.kuri.happygrowing.stats.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuri.happygrowing.R
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
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
        private val mDateTextView: TextView = item.findViewById(R.id.textview_date_stats)
        private val mValueTextView: TextView = item.findViewById(R.id.textview_value_stats)
        private val mSensorIcon: ImageView = item.findViewById(R.id.icon_sensor)

        fun bindItem(measurement: Measurement){
            mValueTextView.text = measurement.stringValue
            mDateTextView.text = SimpleDateFormat(DATE_FORMAT).format(measurement.date)
            mSensorIcon.setImageResource( when(measurement.sensorType){
                 SensorType.TEMPERATURE -> R.drawable.ic_thermometer_medium
                SensorType.HUMIDITY -> R.drawable.ic_humidity_medium
                else -> R.drawable.abc_btn_check_material
            })
        }
    }

}