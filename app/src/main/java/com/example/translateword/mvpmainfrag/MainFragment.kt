package com.example.translateword.mvpmainfrag

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.mvvm.MainViewModel
import com.example.model.data.AppState
import com.example.model.data.DataModel
import com.example.translateword.BaseFragment
import com.example.translateword.R
import com.example.translateword.SearchDialogFragment
import com.example.translateword.databinding.FragmentMainBinding
import com.example.translateword.description.DescriptionActivity
import com.example.translateword.description.convertMeaningsToString
import com.example.translateword.history.HistoryActivity
import com.example.translateword.mvpmainfrag.adapter.MainFragmentAdapter
import com.example.translateword.viewById
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.ext.scope


class MainFragment : BaseFragment<AppState, MainInteractor>() {


    private var binding: FragmentMainBinding? = null
    private var adapter: MainFragmentAdapter? = null
    override lateinit var model: MainViewModel
    private val mainActivityRecyclerview by viewById<RecyclerView>(R.id.main_activity_recyclerview)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_history, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_history -> {
                val intent = Intent(activity, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    private val onListItemClickListener: MainFragmentAdapter.OnListItemClickListener =
        object : MainFragmentAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        activity?.applicationContext,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings!![0].imageUrl
                    )
                )
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(inflater, container, false).also {
        binding = it
        setHasOptionsMenu(true)
        val viewModel: MainViewModel by scope.inject()
        model = viewModel
        model.subscribe().observe(requireActivity(), Observer<AppState> { renderData(it) })
    }.root

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchFAB.setOnClickListener{
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                @SuppressLint("FragmentLiveDataObserve")
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(parentFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
        mainActivityRecyclerview.adapter = adapter

    }

        override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        binding?.apply {
                            mainActivityRecyclerview.layoutManager =
                                LinearLayoutManager(requireContext().applicationContext)
                            mainActivityRecyclerview.adapter =
                                MainFragmentAdapter(onListItemClickListener, dataModel)
                        }
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding?.apply {
                        progressBarHorizontal.visibility = VISIBLE
                        progressBarRound.visibility = GONE
                        progressBarHorizontal.progress = appState.progress!!
                    }
                } else {
                    binding?.apply {
                        progressBarHorizontal.visibility = GONE
                        progressBarRound.visibility = VISIBLE
                    }
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding?.apply {
            errorTextview.text = error ?: getString(R.string.undefined_error)
            reloadButton.setOnClickListener {
                model.getData("hi", true)
            }
        }
    }

    private fun showViewSuccess() {
        binding?.apply {
            successLinearLayout.visibility = VISIBLE
            loadingFrameLayout.visibility = GONE
            errorLinearLayout.visibility = GONE
        }
    }

    private fun showViewLoading() {
        binding?.apply {
            successLinearLayout.visibility = GONE
            loadingFrameLayout.visibility = VISIBLE
            errorLinearLayout.visibility = GONE
        }
    }

    private fun showViewError() {
        binding?.apply {
            successLinearLayout.visibility = GONE
            loadingFrameLayout.visibility = GONE
            errorLinearLayout.visibility = VISIBLE
        }
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }

}