package com.example.translateword.mvpmainfrag

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translateword.BaseFragment
import com.example.translateword.R
import com.example.translateword.SearchDialogFragment
import com.example.translateword.data.AppState
import com.example.translateword.data.DataModel
import com.example.translateword.databinding.FragmentMainBinding
import com.example.translateword.mvpmainfrag.adapter.MainFragmentAdapter
import com.example.translateword.mvvm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<AppState, MainInteractor>() {


    private var binding: FragmentMainBinding? = null
    private var adapter: MainFragmentAdapter? = null
    override lateinit var model: MainViewModel

    private val onListItemClickListener: MainFragmentAdapter.OnListItemClickListener =
        object : MainFragmentAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                //Toast.makeText(requireContext(), data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(inflater, container, false).also {
        binding = it
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(requireActivity(), Observer<AppState> { renderData(it) })
    }.root

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.searchFab?.setOnClickListener {
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
                        progressBarHorizontal.progress = appState.progress
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