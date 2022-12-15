package com.sanket.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

interface IOnListItemClickListener {
    fun onListItemClick(listItem: Any, view: View, viewHolder: ViewHolder, position: Int, bundle: Bundle? = null)
}