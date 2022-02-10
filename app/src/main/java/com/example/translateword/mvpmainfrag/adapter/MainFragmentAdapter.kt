package com.example.translateword.mvpmainfrag.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.data.DataModel
import com.example.translateword.databinding.FragmentMainRecyclerviewItemBinding
import com.example.translateword.description.convertMeaningsToString


class MainFragmentAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<DataModel>
) : RecyclerView.Adapter<MainFragmentAdapter.RecyclerItemViewHolder>() {


    inner class RecyclerItemViewHolder(val binding: FragmentMainRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataModel) {
        if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.apply {
                    headerTextviewRecyclerItem.text = data.text
                    descriptionTextviewRecyclerItem.text = convertMeaningsToString(data.meanings!!)
                }
                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: DataModel) =
        onListItemClickListener.onItemClick(listItemData)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerItemViewHolder(
            FragmentMainRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount() = data.size


    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }

}