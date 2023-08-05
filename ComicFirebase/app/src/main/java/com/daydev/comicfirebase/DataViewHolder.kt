package com.daydev.comicfirebase

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imageView: ImageView

    init {
        imageView = itemView.findViewById(R.id.imageView)
    }

}

