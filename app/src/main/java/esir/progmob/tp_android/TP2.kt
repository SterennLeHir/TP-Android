package esir.progmob.tp_android

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class TP2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tp2_layout1);

        // Création du fichier dans le stockage interne
        val filename = "LEHIR-LECOMTE"
        val fileContent = "Bonjour Sterenn ROUX"
        this.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContent.toByteArray())
        }

        // Récupération des éléments graphiques
        val editText = findViewById<EditText>(R.id.form)
        val buttonOk = findViewById<Button>(R.id.ok)
        val buttonCancel = findViewById<Button>(R.id.cancel)

        // Lecture du fichier
        try {
            val fileToRead : InputStream = openFileInput(filename)
            val size : Int = fileToRead.available()
            val buffer : ByteArray = ByteArray(size)
            fileToRead.read(buffer)
            fileToRead.close()
            val content : String = String(buffer)
            editText.setText(content)
        } catch(e : IOException) {
            e.printStackTrace()
        }

        // Evenement au click de OK
        buttonOk.setOnClickListener {
            val content : String = editText.text.toString()
            this.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(content.toByteArray())
            }
        }

        // Evenement au click de Cancel
        buttonCancel.setOnClickListener {
            // Lecture du fichier et attribution du contenu au texte éditable
            try {
                val fileToRead : InputStream = openFileInput(filename)
                val size : Int = fileToRead.available()
                val buffer : ByteArray = ByteArray(size)
                fileToRead.read(buffer)
                fileToRead.close()
                val content : String = String(buffer)
                editText.setText(content)
            } catch(e : IOException) {
                e.printStackTrace()
            }
        }



    }
}