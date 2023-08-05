package com.daydev.comicfirebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ComicDataAdapter (private val dataModelList: List<DataModel>) : RecyclerView.Adapter<DataViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pages,parent,false))
    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel = dataModelList[position]

        Picasso.get().load(dataModel.photo)
            .error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.imageView)
    }

}
