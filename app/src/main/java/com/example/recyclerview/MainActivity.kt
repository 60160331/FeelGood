package com.example.recyclerview

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_SIGNATURES
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        debugHashKey()

        val fragment_login = fragment_login()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.layout, fragment_login,"fragment_login")
        transaction.addToBackStack("fragment_login")
        transaction.commit()
    }

    override fun onBackPressed() {

        val manager = supportFragmentManager.findFragmentById(R.id.layout)
//when finish fragment_recycler_view will exit app
        if (manager is fragment_recycler_view ) {

            finish()

        }
        else{
            super.onBackPressed()
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("PackageManagerGetSignatures")
    private fun debugHashKey() {
        try {
            val info = packageManager.getPackageInfo(
                "com.example.todolist_android_pj",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.getEncoder().encodeToString(md.digest()))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {
        }

    }

}



