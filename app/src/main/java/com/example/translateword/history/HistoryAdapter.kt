package com.example.translateword.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.model.data.DataModel
import com.example.translateword.databinding.ActivityHistoryRecyclerViewItemBinding
import java.util.*

class HistoryAdapter(
    private var onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>(),
    Filterable {

    private var data: List<DataModel>  = arrayListOf()
    var dataTemp: MutableList<DataModel> = arrayListOf()
    init {
        dataTemp = data.toMutableList()
    }

    @JvmName("setData1")
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DataModel>) {
        this.dataTemp = data.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerItemViewHolder(
            ActivityHistoryRecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(dataTemp[position])
    }

    override fun getItemCount(): Int {
        return dataTemp.size
    }

    inner class RecyclerItemViewHolder(val binding: ActivityHistoryRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.headerHistoryTextviewRecyclerItem.text = data.text
                itemView.setOnClickListener {
                    //Snackbar.make(binding.root, "My Message", Snackbar.LENGTH_SHORT).show()
                    openInNewWindow(data)
                }
            }
        }
    }

    private fun openInNewWindow(listItemData: DataModel) =
        onListItemClickListener.onItemClick(listItemData)

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataTemp = data.toMutableList()
                } else {
                    val resultList = ArrayList<DataModel>()
                    for (row in data) {
                        if (row.equals(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    dataTemp = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataTemp
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataTemp = results?.values as MutableList<DataModel>
                notifyDataSetChanged()
            }

        }
    }
}