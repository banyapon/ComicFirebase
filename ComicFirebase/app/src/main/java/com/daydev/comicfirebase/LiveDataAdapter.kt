package com.daydev.comicfirebase

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class LiveDataAdapter (private val dataModelList: List<LiveDataModel>) : RecyclerView.Adapter<ViewHolder>(){

    val mylist = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.livemodel,parent,false))
    }

    override fun getItemCount(): Int {
        return  dataModelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = dataModelList[position]
        holder.textTitle.text = dataModel.title
        var databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child("channel").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "Count= " + dataSnapshot.childrenCount)
                for (childDataSnapshot in dataSnapshot.children) {
                    Log.d(TAG, "snapshot= " + childDataSnapshot.key!!)
                    mylist.add(childDataSnapshot.key!!)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        Picasso.get().load(dataModel.thumbnail)
            .error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.imageView)

        holder.imageView.setOnClickListener(View.OnClickListener { v ->
            val content = dataModel.content
            val readActivity = Intent(v.context, WatchActivity::class.java)
            readActivity.putExtra("source", content)
            readActivity.putExtra("keys", mylist[position])
            v.context.startActivity(readActivity)
        })

    }
}
