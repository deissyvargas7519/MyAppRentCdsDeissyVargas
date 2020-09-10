package com.example.myapprentcds


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.text.TextUtils
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.myapprentcds.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main0.*
import kotlin.properties.Delegates

class MainActivity0 : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtApellido: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main0)

        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        progressBar = ProgressBar(this)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference = database.reference.child("Usuario")
    }

    fun register(view: View) {
        createNewAccount()
    }

    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun verifyEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                if (task.isComplete) {
                    Toast.makeText(
                        this,
                        "Email Enviado",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Error al verificar el correo ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun createNewAccount() {
        val Nombre: String = txtNombre.text.toString()
        val Apellido: String = txtApellido.text.toString()
        val email: String = txtEmail.text.toString()
        val password: String = txtPassword.text.toString()


        if (!TextUtils.isEmpty(Nombre) && !TextUtils.isEmpty(Apellido)) {
            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isComplete) {
                        val user: FirebaseUser? = auth.currentUser
                        verifyEmail(user)
                        val UserDb = dbReference.child(user?.uid.toString())
                        UserDb.child("Nombre").setValue(Nombre)
                        UserDb.child("Apellido").setValue(Apellido)

                        goHome()

                    } else {
                        Toast.makeText(this, "Diligencie todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
}

