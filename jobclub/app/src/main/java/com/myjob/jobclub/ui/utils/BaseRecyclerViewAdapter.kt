package com.myjob.jobclub.ui.utils

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {
    var data: ArrayList<T> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addAll(items: List<T>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }

    /***
     * @Method to get Generic item based on the position
     * @param position of Expected item
     * @return Generic object based on adapter data
     * ***/
    fun getItem(position: Int): T {
        return data[position]
    }

    /***
     * @Method to get the position of Generic item
     * @param Generic object based on adapter data
     * @return Position of Expected item
     * ***/
    fun indexOf(element: T): Int {
        return data.indexOf(element)
    }

    override fun getItemCount(): Int {
        return data.let { data.size }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}