package com.example.androidtd

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.androidtd.network.Api
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class UserInfoActivity : AppCompatActivity() {
    private val coroutineScope = MainScope()


    companion object {
        const val CAMERA_PERMISSION_CODE = 1000
        const val CAMERA_REQUEST_CODE = 2001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        take_picture_button.setOnClickListener {askCameraPermissionAndOpenCamera()  }
    }

    private fun askCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE )
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE )
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==PackageManager.PERMISSION_GRANTED) openCamera()
        else Toast.makeText(this, "We need access to your camera to take a picture :'(", Toast.LENGTH_LONG).show()
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        handlePhotoTaken(data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handlePhotoTaken(data: Intent?) {
        val image = data?.extras?.get("data") as? Bitmap
        val imageBody = imageToBody(image)


        // Afficher l'image
        Glide.with(this).load(image).into(avatar_image)

        // Plus tard : Envoie de l'avatar au serveur
        if (imageBody != null) {
            coroutineScope.launch {
                Api.userService.updateAvatar(imageBody)
            }
        }
    }

    private fun imageToBody(image: Bitmap?): MultipartBody.Part? {
        val f = File(cacheDir, "tmpfile.jpg")
        f.createNewFile()
        try {
            val fos = FileOutputStream(f)
            image?.compress(Bitmap.CompressFormat.PNG, 100, fos)

            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()

        }

        val body = RequestBody.create(MediaType.parse("image/png"), f)
        return MultipartBody.Part.createFormData("avatar", f.path ,body)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

}
