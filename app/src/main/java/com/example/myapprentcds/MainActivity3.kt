package com.example.myapprentcds

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main3.*
import java.util.*



class MainActivity3 : AppCompatActivity() {
    private lateinit var txtDni: EditText
    private lateinit var txtNombre: EditText
    private lateinit var txtApellido: EditText
    private lateinit var txtCodigo: EditText
    private lateinit var txtDireccion: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var txtTemasInteres: EditText
    private lateinit var txtEstado: EditText
    private lateinit var BtnGuardarDatos: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    var FechaNacimiento: EditText?=null
    private var mYearIni=0
    private var mMonthIni=0
    private var mDayIni=0
    private var sYearIni=0
    private var sMonthIni=0
    private var sDayIni=0
    var C=Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


        txtDni = findViewById(R.id.txtDni)
        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtCodigo = findViewById(R.id.txtCodigo)
        txtDireccion = findViewById(R.id.txtDireccion)
        txtTelefono = findViewById(R.id.txtTelefono)
        txtTemasInteres = findViewById(R.id.txtTemasInteres)
        txtEstado = findViewById(R.id.txtEstado)
        BtnGuardarDatos = findViewById(R.id.BtnGuardarDatos)
        sMonthIni=C[Calendar.MONTH]
        sDayIni=C[Calendar.DAY_OF_MONTH]
        sYearIni=C[Calendar.YEAR]
        FechaNacimiento=findViewById(R.id.txtFechaNacimiento) as EditText?


        FechaNacimiento!!.setOnClickListener { showDialog(MainActivity3.Companion.DATE_ID) }

        progressBar = ProgressBar(this)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()
        val ElementReference = database.reference.child("Clientes").push()



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
                    TAG,

                    "Failed to read value.",
                    databaseError.toException()
                )
            }
        })


        BtnGuardarDatos.setOnClickListener {

            val Dni: String = txtDni.text.toString()
            val Nombre: String = txtNombre.text.toString()
            val Apellido: String = txtApellido.text.toString()
            val Codigo: String = txtCodigo.text.toString()
            val Direccion: String = txtDireccion.text.toString()
            val Telefono: String = txtTelefono.text.toString()
            val TemasInteres: String = txtTemasInteres.text.toString()
            val Estado: String = txtEstado.text.toString()


            ElementReference.child("Dni").setValue(Dni)
            txtDni.setText("")

            ElementReference.child("Nombre").setValue(Nombre)
            txtNombre.setText("")

            ElementReference.child("Apellido").setValue(Apellido)
            txtApellido.setText("")

            ElementReference.child("Codigo").setValue(Codigo)
            txtCodigo.setText("")

            ElementReference.child("Direccion").setValue(Direccion)
            txtDireccion.setText("")

            ElementReference.child("FechaNacimiento").setValue(FechaNacimiento)
            txtFechaNacimiento.setText("")

            ElementReference.child("TemasInteres").setValue(TemasInteres)
            txtTemasInteres.setText("")

            ElementReference.child("Estado").setValue(Estado)
            txtEstado.setText("")


            if (Dni.isEmpty() || Nombre.isEmpty() || Apellido.isEmpty() || Codigo.isEmpty()
                || Apellido.isEmpty() || Nombre.isEmpty() || Apellido.isEmpty()
                || Direccion.isEmpty() || Telefono.isEmpty()
                || Apellido.isEmpty()
                || Direccion.isEmpty() || Telefono.isEmpty()
                || TemasInteres.isEmpty()
                || Estado.isEmpty()) {
                Toast.makeText(this, "Debe diligenciar todos los campos", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(
                    this,
                    "Se han Guardado los datos, si desea actualizar esta informacion ingresela de nuevo y se reemplazara",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }


    fun showMessage(msg: String) {
        Toast.makeText(this@MainActivity3, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val DATE_ID=0
        private val TAG = MainActivity3::class.java.simpleName
    }

    private fun colocar_fecha_Nac() {
        FechaNacimiento!!.setText((mDayIni + 1).toString() + "-" + mMonthIni + "-" + mYearIni + " ")
    }

    private val mDateSetListener=
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                mYearIni=year
                mMonthIni=monthOfYear
                mDayIni=dayOfMonth
                colocar_fecha_Nac()
            }

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            MainActivity3.Companion.DATE_ID -> return DatePickerDialog(
                this,
                mDateSetListener,
                sYearIni,
                sMonthIni,
                sDayIni
            )
        }
        return null
    }

}

