package com.example.myapprentcds


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.myapprentcds.R
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var textPassword: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtEmail=findViewById(R.id.txtEmail)
        textPassword=findViewById(R.id.txtPassword)
        auth=FirebaseAuth.getInstance()
        progressBar=ProgressBar(this)
    }

    private fun loginUser() {
        val email=txtEmail.text.toString()
        val password:String=textPassword.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        task ->
                        if (task.isSuccessful) {
                            progressBar.visibility=View.VISIBLE

                            action()
                        } else {
                            Toast.makeText(this, "Error en la autenticación.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(this, "Debe diligenciar todos los campos.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun action() {
        startActivity(Intent(this,MainActivity2::class.java))
    }

    fun login(view: View) {
        loginUser()
    }


    fun forgotPassword(view: View) {
        startActivity(Intent(this, MainActivity1::class.java))
    }


    fun register(view: View) {
        startActivity(Intent(this, MainActivity0::class.java))
    }
}
