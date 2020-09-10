package com.example.myapprentcds


import android.content.ContentValues.TAG
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main6.*

class MainActivity6 : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private lateinit var txtGenero: EditText
    private lateinit var BtnGuardarDatos: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    var Genero = arrayOf("Rock", "Pop", "Balada", "Salsa", "Vallenato", "Samba")
    var spnKotlin: Spinner? = null
    var textView_msg: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        txtGenero = findViewById(R.id.txtGenero)
        BtnGuardarDatos = findViewById(R.id.BtnGuardarDatos)
        progressBar = ProgressBar(this)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        textView_msg = this.txtSpinner
        spnKotlin = this.spinnerKotlin
        spnKotlin!!.setOnItemSelectedListener(this)

        database = FirebaseDatabase.getInstance()
        val ElementReference = database.reference.child("Cds").push()

        ElementReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
                showMessage(dataSnapshot.value.toString())
            }

            override fun onChildChanged(
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(
                    MainActivity6.Companion.TAG,

                    "Failed to read value.",
                    databaseError.toException()
                )
            }
        })


        BtnGuardarDatos.setOnClickListener {

            val Genero: String = txtGenero.text.toString()

            ElementReference.child("Genero").setValue(Genero)
            txtGenero.setText("")

            if (Genero.isEmpty()) {
                Toast.makeText(this, "Debe diligenciar todos los campos", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Se ha Agregado un nuevo genero, para modificarlo ingrese otro genero",
                    Toast.LENGTH_SHORT).show()
            }

        }

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, Genero)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnKotlin!!.setAdapter(aa)

        val linearLayout = findViewById<LinearLayout>(R.id.linearDinamico)
        val spinner = Spinner(this)
        spinner.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        fun onNothingSelected(parent: AdapterView<*>) {
//
        }
    }

    companion object {
        private val TAG = MainActivity6::class.java.simpleName
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        textView_msg!!.text = "El Cd Seleccionado es del Genero : " + Genero[position]

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }


    fun showMessage(msg: String) {
        Toast.makeText(this@MainActivity6, msg, Toast.LENGTH_SHORT).show()
    }

   
}


