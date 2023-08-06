package com.daydev.comicfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var response_data: MutableList<DataModel>
    private var dataAdapter: DataAdapter? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    private var buttonVideo: Button? = null
    private var buttonUpload: Button? = null

    override fun onStart(){
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null){
            val uid = currentUser.uid
            val email = currentUser.email
        }else{
            val intentSession = Intent(this, LoginActivity::class.java)
            startActivity(intentSession)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = GridLayoutManager(this, 2)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("app/data")
        response_data = mutableListOf()

        dataAdapter = DataAdapter(response_data as ArrayList<DataModel>)
        recyclerView!!.adapter = dataAdapter
        bindingData()

        buttonVideo = findViewById<Button>(R.id.buttonVideo)
        buttonVideo!!.setOnClickListener {
            var videoView = Intent(this,LiveActivity::class.java)
            startActivity(videoView)
        }
        buttonUpload = findViewById<Button>(R.id.buttonUpload)
        buttonUpload!!.setOnClickListener {
            var uploadMedia = Intent(this,UploadActivity::class.java)
            startActivity(uploadMedia)
        }


    }

    private fun bindingData() {
        databaseReference!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                response_data!!.add(snapshot.getValue(DataModel::class.java)!!)
                dataAdapter!!.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) { }

            override fun onChildRemoved(snapshot: DataSnapshot) { }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }

            override fun onCancelled(error: DatabaseError) { }
        })
    }
}