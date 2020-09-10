package com.example.myapprentcds

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapprentcds.MainActivity3
import com.example.myapprentcds.MainActivity4
import com.example.myapprentcds.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        BtnDatos.setOnClickListener({
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        })

        BtnAlquileres.setOnClickListener({
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        })

        BtnSanciones.setOnClickListener({
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        })

        BtnLista.setOnClickListener({
            val intent = Intent(this, MainActivity6::class.java)
            startActivity(intent)
        })

    }
}