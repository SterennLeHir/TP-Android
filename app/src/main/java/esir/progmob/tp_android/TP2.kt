package esir.progmob.tp_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import java.io.File
import java.io.FileOutputStream


class TP2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tp2_layout1);

        // Récupération du répertoire du stockage interne
        val dir : File = this.getFilesDir();

        val file : File = File(dir, "fichier")

        val fileToWrite : FileOutputStream = FileOutputStream(file)



    }
}