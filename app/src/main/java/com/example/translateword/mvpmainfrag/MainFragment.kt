package com.example.translateword.mvpmainfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translateword.*
import com.example.translateword.databinding.FragmentMainBinding

class MainFragment : BaseFragment<AppState>() {

    private var binding: FragmentMainBinding? = null
    private var adapter: MainFragmentAdapter? = null
    private val onListItemClickListener: MainFragmentAdapter.OnListItemClickListener =
        object : MainFragmentAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(requireContext(), data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.searchFab?.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(parentFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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

    override fun createPresenter(): Presenter<AppState, com.example.translateword.View> {
        return MainFragmentPresenterImpl()
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding?.errorTextview?.text = error ?: getString(R.string.undefined_error)
        binding?.reloadButton?.setOnClickListener {
            presenter.getData("hi", true)
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