package esir.progmob.tp_android

import android.content.Context
import android.os.Bundle
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

        val filename = "LEHIR-LECOMTE"
        val fileContents = "Bonjour Sterenn ROUX"
        this.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }

        try {
            val fileToRead : InputStream = openFileInput(filename)
            val size : Int = fileToRead.available()
            val buffer : ByteArray = ByteArray(size)
            fileToRead.read(buffer)
            fileToRead.close()
            val content : String = String(buffer)
        } catch(e : IOException) {
            e.printStackTrace()
        }




    }
}