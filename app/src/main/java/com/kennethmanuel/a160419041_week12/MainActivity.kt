package com.kennethmanuel.a160419041_week12

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    val REQUEST_SELECT_CONTACT = 1
    val REQUEST_SELECT_TEMPLATE = 2
    val REQUEST_IMAGE_CAMERA = 3

    private fun takePicture() {
        val intent = Intent().apply {
            action = MediaStore.ACTION_IMAGE_CAPTURE
        }
        startActivityForResult(intent, REQUEST_IMAGE_CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_IMAGE_CAMERA -> {
                if(grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED)
                    takePicture()
                else
                    Toast.makeText(this, "Anda harus memberikan izin untuk mengakses kamera.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonSend.setOnClickListener {
            // Buat send intent
            val sendIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                //putExtra(Intent.EXTRA_TEXT, editPesan.text.toString())
                //type = "text/plain"
                //`package` = "com.whatsapp"
                data = Uri.parse("https://api.whatsapp.com/send?phone=${editHP.text}&text=${editPesan.text}")
            }
            // Buat share intent
            val shareIntent = Intent.createChooser(sendIntent, "Kirim pesan menggunakan")
            startActivity(shareIntent)
        }

        inputHP.setEndIconOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            }
            startActivityForResult(intent, REQUEST_SELECT_CONTACT)
        }
        buttonTemplate.setOnClickListener {
            val intent = Intent(this, MessageTemplateActivity::class.java)
            startActivityForResult(intent, REQUEST_SELECT_TEMPLATE)
        }
        fabFoto.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_IMAGE_CAMERA)
            } else {
                takePicture()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_CONTACT -> {
                    val contactUri = data?.data
                    val projection = arrayOf(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    val cursor =
                        contactUri?.let {
                            contentResolver.query(it, projection, null, null, null)
                        }
                    cursor?.let {
                        if(it.moveToFirst()) {
                            val hp = it.getString(0)
                            editHP.setText(hp)
                        }
                    }
                }
                REQUEST_SELECT_TEMPLATE -> {
                    val pesan = data?.getStringExtra(MessageTemplateActivity.EXTRA_TEMPLATE)
                    editPesan.setText(pesan)
                }
                REQUEST_IMAGE_CAMERA -> {
                    data?.extras?.let {
                        val imageBitmap = it.get("data") as Bitmap
                        imagePhoto.setImageBitmap(imageBitmap)
                    }
                }
            }
        }
    }
}