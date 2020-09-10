package com.example.myapprentcds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.text.TextUtils
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main1.*
import kotlin.properties.Delegates
import androidx.core.content.ContextCompat.startActivity
import com.example.myapprentcds.R

class MainActivity1 : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        txtEmail=findViewById(R.id.txtEmail)
        auth=FirebaseAuth.getInstance()
        progressBar=ProgressBar(this)
    }

    fun send(view: View) {
        val email=txtEmail.text.toString()
        if (!TextUtils.isEmpty(email)) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this) {
                        task ->
                    if (task.isSuccessful) {
                        progressBar.visibility=View.VISIBLE
                        startActivity(Intent(this,MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "Error al enviar Email.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Debe diligenciar todos los campos.", Toast.LENGTH_SHORT).show()
        }
    }

}
