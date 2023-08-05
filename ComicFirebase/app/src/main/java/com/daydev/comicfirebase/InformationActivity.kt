package com.daydev.comicfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class InformationActivity : AppCompatActivity() {
    private lateinit var response_data: MutableList<DataModel>
    private var dataAdapter: ComicDataAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    private var get_key: String? = null

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val intent = intent
        get_key = intent.getStringExtra("keys")

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("app/data/$get_key/pages")

        response_data = ArrayList()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView = findViewById<RecyclerView>(R.id.recycleComic)
        mRecyclerView!!.layoutManager = layoutManager
        dataAdapter = ComicDataAdapter(response_data)
        mRecyclerView!!.adapter = dataAdapter

        comicBindingData()

    }

    private fun comicBindingData() {
        databaseReference!!.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                response_data!!.add(snapshot.getValue(DataModel::class.java)!!)
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