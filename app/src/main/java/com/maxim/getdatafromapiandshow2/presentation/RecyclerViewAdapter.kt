package com.maxim.getdatafromapiandshow2.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maxim.getdatafromapiandshow2.R

class RecyclerViewAdapter(
    private val communication: ListCommunication
) : RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolder>() {


    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: UiItem) {
            item.show(itemView.findViewById<TextView>(R.id.savedDataText))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (communication.getList()[position] is UiItem.FailedUiItem) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val emptyList = viewType == 0
        val view = LayoutInflater.from(parent.context).inflate(
            if(emptyList)
                R.layout.saved_data_empty_list
            else
                R.layout.saved_data_view,
            parent, false
        )
        return DataViewHolder(view)
    }

    override fun getItemCount() = communication.getList().size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(communication.getList()[position])
    }

    fun update() {
        communication.getDiffResult().dispatchUpdatesTo(this)
    }
}

class DiffUtilCallback(
    private val oldList: List<UiItem>,
    private val newList: List<UiItem>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].same(newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }
}