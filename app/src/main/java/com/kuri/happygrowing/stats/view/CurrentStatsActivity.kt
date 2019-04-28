package com.kuri.happygrowing.stats.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuri.happygrowing.R
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import com.kuri.happygrowing.stats.viewmodel.CurrentStatsViewModel
import com.kuri.happygrowing.stats.viewmodel.StatsViewModelFactory
import java.util.*

class CurrentStatsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: StatsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_stats)

        viewManager = LinearLayoutManager(this)
        viewAdapter = StatsAdapter(listOf(Measurement(10.0f, Date(), "temperature")))

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_current_stats).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val viewModel = ViewModelProviders.of(this, StatsViewModelFactory).get(CurrentStatsViewModel::class.java)
        viewModel.measurements.observe(this, androidx.lifecycle.Observer {
            viewAdapter.updateValues(
                SensorType.values().asSequence().filter { type -> it.containsKey(type) && it[type] != null }.map { type -> it[type] ?: error("") }.toList()
            )
        })
    }


}
