package com.example.myapprentcds



import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main4.*
import kotlinx.android.synthetic.main.activity_main6.*
import kotlinx.android.synthetic.main.activity_main6.spinnerKotlin
import kotlinx.android.synthetic.main.activity_main6.txtSpinner
import java.util.*
import kotlinx.android.synthetic.main.activity_main6.txtresultado as txtresultado1

class MainActivity4 : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private lateinit var txtDias: EditText
    private lateinit var BtnAlquilarDatos: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    var Genero = arrayOf("Rock", "Pop", "Balada", "Salsa", "Vallenato", "Samba")
    var spnKotlin: Spinner? = null
    private var textView_msg: TextView? = null
    var FechaAlquiler: EditText?=null
    private var mYearIni=0
    private var mMonthIni=0
    private var mDayIni=0
    private var sYearIni=0
    private var sMonthIni=0
    private var sDayIni=0
    var C=Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        txtDias = findViewById(R.id.txtDias)
        BtnAlquilarDatos = findViewById(R.id.BtnAlquilarDatos)
        progressBar = ProgressBar(this)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        textView_msg =this.txtSpinner
        spnKotlin = this.spinnerKotlin
        spnKotlin!!.setOnItemSelectedListener(this)
        sMonthIni=C[Calendar.MONTH]
        sDayIni=C[Calendar.DAY_OF_MONTH]
        sYearIni=C[Calendar.YEAR]
        FechaAlquiler=findViewById(R.id.txtFechaAlquiler) as EditText?

        FechaAlquiler!!.setOnClickListener { showDialog(MainActivity3.Companion.DATE_ID) }

        database = FirebaseDatabase.getInstance()
        val ElementReference = database.reference.child("Alquier").push()

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
                    MainActivity4.Companion.TAG,

                    "Failed to read value.",
                    databaseError.toException()
                )
            }
        })

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
        BtnAlquilarDatos.setOnClickListener {
            val FechaAlquiler: String = txtFechaAlquiler!!.text.toString()
            val Dias: String = txtDias!!.text.toString()

            ElementReference.child("Dias").setValue(Dias)
            txtDias.setText("")


            if (Dias<=4.toString() ) {
                Toast.makeText(this, "El costo es de 8.000 pesos", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "El costo es de 12.000 pesos",
                    Toast.LENGTH_SHORT).show()
            }
            var total=""
            if (Dias.isEmpty()) {
                Toast.makeText(this, "Debe diligenciar todos los campos", Toast.LENGTH_LONG).show()
            }
            else{
                total = ((Dias.toInt() + sDayIni.toInt()).toString())
                txtresultado.text = "Debe realizar la Devolucion del Cd para el Dia " + total.toString() + ", Si no se realiza este dia podria tener una Sancion"
            }
        }

    }



    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        textView_msg!!.text = "El Cd Seleccionado es del Genero : " + Genero[position]

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }


    fun showMessage(msg: String) {
        Toast.makeText(this@MainActivity4, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val DATE_ID=0
        private val TAG = MainActivity4::class.java.simpleName
    }

    private fun colocar_fecha_Nac() {
        FechaAlquiler!!.setText((mDayIni + 1).toString() + "-" + mMonthIni + "-" + mYearIni + " ")
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
            MainActivity4.Companion.DATE_ID -> return DatePickerDialog(
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


