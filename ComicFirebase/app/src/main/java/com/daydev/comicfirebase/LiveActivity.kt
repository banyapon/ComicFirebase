package com.daydev.comicfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LiveActivity : AppCompatActivity() {
    var buttonComic: Button?=null

    private val TAG = "TV"
    private lateinit var response_data: MutableList<LiveDataModel>
    private var dataAdapter: LiveDataAdapter? = null

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private var liveRecyclerView: RecyclerView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)

        buttonComic = findViewById<Button>(R.id.buttonComic)
        buttonComic!!.setOnClickListener {
            var readComic = Intent(this,MainActivity::class.java)
            startActivity(readComic)
        }

        liveRecyclerView = findViewById<RecyclerView>(R.id.liverecyclerView)
        liveRecyclerView!!.layoutManager = LinearLayoutManager(this)
        liveRecyclerView!!.setLayoutManager(GridLayoutManager(this, 3))


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("channel")
        response_data = mutableListOf()

        dataAdapter = LiveDataAdapter(response_data as ArrayList<LiveDataModel>)
        liveRecyclerView!!.setAdapter(dataAdapter)
        bindingData()
    }

    private fun bindingData() {
        databaseReference!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                response_data!!.add(snapshot.getValue(LiveDataModel::class.java)!!)
                dataAdapter!!.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}