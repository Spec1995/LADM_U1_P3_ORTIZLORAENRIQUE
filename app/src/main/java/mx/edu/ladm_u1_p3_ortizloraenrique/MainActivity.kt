package mx.edu.ladm_u1_p3_ortizloraenrique

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var vector = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this,Manifest
                .permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest
                .permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }
        else{
            mensaje("¡¡PERMISOS OTORGADOS !!")
        }

        btnAsignar.setOnClickListener {
            if (valorInsertar.text.isEmpty()== true || vectorPosicion.text.isEmpty() ==true) {
                Toast.makeText(this, "llene los campos",
                    Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            var valor = valorInsertar.text.toString()
            val posicion: Int = vectorPosicion.text.toString().toInt() - 1
            vector.add(posicion, valor)
            Toast.makeText(this, "valor capturado",
                Toast.LENGTH_LONG)
                .show()
        }
        btnMostrar_Todos.setOnClickListener {
            listViewMostrar()

        }
        btnGuardarSD.setOnClickListener {
            guardarArchivoSD()
        }

        btnLeerSD.setOnClickListener {
            Toast.makeText(this, "archivo leido ", Toast.LENGTH_LONG).show()
            leerArchivoSD()
        }
    }

    private fun guardarArchivoInterno() {
        try {
            var flujoSalida =
                OutputStreamWriter(openFileOutput(txtGuardar.text.toString()
                    ,Context.MODE_PRIVATE))
            var data = vector.size - 1
            flujoSalida.write(data)
            flujoSalida.flush()
            flujoSalida.close()
            mensaje("¡¡Archivo creado!!")
            asignarText("")
        } catch (e: Exception) {
            mensaje(e.message.toString())}}

    fun guardarArchivoSD() {
        if (noSD()) {
            mensaje("No hay memoria")
            return
        }
        try {
            var rutaSD = Environment.getExternalStorageDirectory()
            var datosArchivo = File(rutaSD.absolutePath, txtGuardar.text.toString())
            var flujoSalida = OutputStreamWriter(FileOutputStream(datosArchivo))
            var data = ""
            (0..9).forEach {vec ->
                data += vector[vec]}

            flujoSalida.write(data)
            flujoSalida.flush()
            flujoSalida.close()
            mensaje("¡¡Archivo creado!!")
            asignarText("")
        } catch (e: Exception) {
            mensaje(e.message.toString())
        }
    }
    fun leerArchivoSD() {
        if (noSD()) {
            return
        }
        try {
            var rutaSD = Environment.getExternalStorageDirectory()
            var datosArchivo = File(rutaSD.absolutePath, txtLeer.text.toString())
            var flujoEnntrada = BufferedReader(InputStreamReader(FileInputStream(datosArchivo)))
            var data = flujoEnntrada.readLine()
            var vector2 = ArrayList<String>()
            var total = vector2.size - 1
            vector2.add(data)
            var datosVector: Array<String> = Array(vector2.size, {" "})
            (0..total).forEach { vec2 ->
                datosVector[vec2] = vector2[vec2]
                datosVector[vec2] = datosVector[vec2].replace("2", "\n");
            }
            mensaje(vector.toString() + "vector normal")
            mensaje(vector2.toString() + "vector2")
        } catch (e: Exception) {
            mensaje(e.message.toString())}}

    fun noSD(): Boolean {
        var estado = Environment.getExternalStorageState()
        if (estado != Environment.MEDIA_MOUNTED) {
            return true
        } else {
            return false
        }
    }
    fun listViewMostrar() {
        var total = vector.size - 1
        var datosVector: Array<String> = Array(
            vector.size, {" "})
        (0..total).forEach { lis ->
            datosVector[lis] = vector[lis]
            datosVector[lis] = datosVector[lis].replace("&", "\n");
        }
        var adpListView =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datosVector)
        lista.adapter = adpListView
    }
    fun mensaje(m: String) {
        AlertDialog.Builder(this).setTitle("ATENCION").setMessage(m)
            .setPositiveButton("ACEPTAR") { d, i ->
            }.show() }

    fun asignarText(t1: String) {
        valorInsertar.setText(t1)
        Toast.makeText(this, "posicion de arreglo = " + t1, Toast.LENGTH_LONG).show()
    }

}
