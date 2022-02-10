package com.example.translateword.history

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.model.data.AppState
import com.example.model.data.DataModel
import com.example.translateword.databinding.ActivityHistoryBinding
import com.example.translateword.description.DescriptionActivity
import com.example.translateword.view.BaseActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    private lateinit var binding: ActivityHistoryBinding
    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter(onListItemClickListener) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniViewModel()
        initViews()


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }

        })
    }

    private val onListItemClickListener: HistoryAdapter.OnListItemClickListener =
        object : HistoryAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@HistoryActivity,
                        "Cut",
                        "cutting",
                        "uywoeioq"
                    )
                )
            }
        }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }


    override fun setDataToAdapter(data: List<DataModel>) {
        adapter?.setData(data)
    }

    private fun iniViewModel() {
        if (binding.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }


}