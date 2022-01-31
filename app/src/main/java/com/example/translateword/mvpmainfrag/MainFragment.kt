package com.example.translateword.mvpmainfrag

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translateword.*
import com.example.translateword.databinding.FragmentMainBinding
import com.example.translateword.mvvm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<AppState, MainInteractor>() {


    private var binding: FragmentMainBinding? = null
    private var adapter: MainFragmentAdapter? = null
    override lateinit var model: MainViewModel


    private val observer = Observer<AppState> { renderData(it) }


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
                    model.getData(searchWord, true).observe(this@MainFragment, observer)
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
                        binding?.mainActivityRecyclerview?.layoutManager =
                            LinearLayoutManager(requireContext().applicationContext)
                        binding?.mainActivityRecyclerview?.adapter =
                            MainFragmentAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding?.progressBarHorizontal?.visibility = VISIBLE
                    binding?.progressBarRound?.visibility = GONE
                    binding?.progressBarHorizontal?.progress = appState.progress
                } else {
                    binding?.progressBarHorizontal?.visibility = GONE
                    binding?.progressBarRound?.visibility = VISIBLE
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
        binding?.errorTextview?.text = error ?: getString(R.string.undefined_error)
        binding?.reloadButton?.setOnClickListener {
            model.getData("hi", true).observe(requireActivity(), observer)
        }
    }

    private fun showViewSuccess() {
        binding?.successLinearLayout?.visibility = VISIBLE
        binding?.loadingFrameLayout?.visibility = GONE
        binding?.errorLinearLayout?.visibility = GONE
    }

    private fun showViewLoading() {
        binding?.successLinearLayout?.visibility = GONE
        binding?.loadingFrameLayout?.visibility = VISIBLE
        binding?.errorLinearLayout?.visibility = GONE
    }

    private fun showViewError() {
        binding?.successLinearLayout?.visibility = GONE
        binding?.loadingFrameLayout?.visibility = GONE
        binding?.errorLinearLayout?.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }



}