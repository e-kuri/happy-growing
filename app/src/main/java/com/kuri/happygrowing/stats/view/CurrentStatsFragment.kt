package com.kuri.happygrowing.stats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuri.happygrowing.R
import com.kuri.happygrowing.shared.callback.OnResultCallback
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.viewmodel.CurrentStatsViewModel
import com.kuri.happygrowing.stats.viewmodel.StatsViewModelFactory
import java.util.*

class CurrentStatsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: StatsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: CurrentStatsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_current_stats, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewManager = LinearLayoutManager(context)
        viewAdapter = StatsAdapter(listOf(Measurement(10.0f, Date(), "temperature")))

        recyclerView = activity!!.findViewById<RecyclerView>(R.id.recyclerview_current_stats).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val onSensorUpdateCallback = object: OnResultCallback<List<Measurement>>{

            override fun onSuccessResult(result: List<Measurement>) {
                viewAdapter.updateValues(result)
            }

            override fun onError(e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG)
            }
        }

        viewModel = StatsViewModelFactory.getViewModel(this, onSensorUpdateCallback)
    }


}
