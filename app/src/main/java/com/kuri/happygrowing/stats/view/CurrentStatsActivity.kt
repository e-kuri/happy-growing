package com.kuri.happygrowing.stats.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuri.happygrowing.R
import com.kuri.happygrowing.stats.model.Measurement
import java.util.*

class CurrentStatsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_stats)

        viewManager = LinearLayoutManager(this)
        viewAdapter = StatsAdapter(arrayOf(Measurement(10.0f, Date(), "TEMPERATURE")))

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_current_stats).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
