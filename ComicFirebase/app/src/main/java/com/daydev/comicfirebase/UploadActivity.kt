package com.daydev.comicfirebase

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


@Suppress("DEPRECATION")
class UploadActivity : AppCompatActivity() {

    var chooseImg: Button? = null
    var uploadImg:Button? = null
    var imgView: ImageView? = null
    var PICK_IMAGE_REQUEST = 111
    var filePath: Uri? = null
    var progressDialog: ProgressDialog? = null

    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef: StorageReference = storage.getReferenceFromUrl("gs://gangbanggame.appspot.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        chooseImg = findViewById<Button>(R.id.chooseImg);
        uploadImg = findViewById<Button>(R.id.uploadImg);
        imgView = findViewById<ImageView>(R.id.imgView);

        chooseImg!!.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(
                Intent.createChooser(intent, "Select Image"),
                PICK_IMAGE_REQUEST
            )
        }
        uploadImg!!.setOnClickListener {
            if (filePath != null) {

               progressDialog!!.show()

                //Random Function เพื่อไปแทน image.jpg
                val childRef = storageRef.child("image.jpg")
                //uploading the image
                val uploadTask = childRef.putFile(filePath!!)
                uploadTask.addOnSuccessListener {
                    progressDialog!!.dismiss()
                    Toast.makeText(this@UploadActivity, "Upload successful", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener { e ->
                    progressDialog!!.dismiss()
                    Toast.makeText(this@UploadActivity, "Upload Failed -> $e", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {

                Toast.makeText(this@UploadActivity, "Select an image", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        filePath = data!!.data
        try {
            //getting image from gallery
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)

            //Setting image to ImageView
            imgView!!.setImageBitmap(bitmap)

            //intent เปลี่ยนหน้า
            Toast.makeText(this@UploadActivity, "Upload เรียบร้อย!", Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}