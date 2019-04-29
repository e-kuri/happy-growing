package com.kuri.happygrowing.stats.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuri.happygrowing.R
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import com.kuri.happygrowing.stats.viewmodel.CurrentStatsViewModel
import com.kuri.happygrowing.stats.viewmodel.StatsViewModelFactory
import java.util.*

class CurrentStatsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: StatsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: CurrentStatsViewModel


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

        viewModel = StatsViewModelFactory.getViewModel(this,
            object: OnResultCallback<Map<SensorType, Measurement>>{

                override fun onSuccessResult(result: Map<SensorType, Measurement>) {
                    viewAdapter.updateValues(
                    SensorType.values().asSequence().filter {
                            type -> result.containsKey(type) && result[type] != null
                    }.map { type -> result[type] ?: error("") }.toList())
                }

                override fun onError(e: Exception) {
                    Toast.makeText(this@CurrentStatsActivity, e.message, Toast.LENGTH_LONG)
                }
        })
    }


}
