package com.maxim.getdatafromapiandshow2.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maxim.getdatafromapiandshow2.R

class RecyclerViewAdapter(
    private val listener: Listener,
    private val communication: ListCommunication
) : RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolder>() {


    abstract class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(item: UiItem) {
            item.show(itemView.findViewById<TextView>(R.id.savedDataText))
        }

        class Base(view: View, private val listener: Listener) : DataViewHolder(view) {
            override fun bind(item: UiItem) {
                super.bind(item)
                itemView.findViewById<ImageView>(R.id.deleteButton).setOnClickListener {
                    item.change(listener)
                }
            }
        }
    }

    inner class EmptyViewHolder(view: View) : DataViewHolder(view)


    override fun getItemViewType(position: Int): Int {
        return if (communication.getList()[position] is UiItem.FailedUiItem) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val emptyList = viewType == 0
        val view = LayoutInflater.from(parent.context).inflate(
            if (emptyList)
                R.layout.saved_data_empty_list
            else
                R.layout.saved_data_view,
            parent, false
        )
        return if (emptyList) EmptyViewHolder(view) else DataViewHolder.Base(view, listener)
    }

    override fun getItemCount() = communication.getList().size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(communication.getList()[position])
    }

    fun update() {
        communication.getDiffResult().dispatchUpdatesTo(this)
    }

    interface Listener {
        fun change(text: String)
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