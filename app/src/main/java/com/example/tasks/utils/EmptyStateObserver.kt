package com.example.tasks.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyStateObserver constructor(rv : RecyclerView?,ev: View?) : RecyclerView.AdapterDataObserver() {

   private var recyclerView : RecyclerView? = null
    private var emptyView : View? = null

    init {
      recyclerView = rv
      emptyView = ev
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (emptyView != null && recyclerView!!.adapter != null) {
            val emptyViewVisible = recyclerView!!.adapter!!.itemCount <= 0
            emptyView!!.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            recyclerView!!.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        }
    }


    override fun onChanged() {
        checkIfEmpty()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        checkIfEmpty()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        checkIfEmpty()
    }


}