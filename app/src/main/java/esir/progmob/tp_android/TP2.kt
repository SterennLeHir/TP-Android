package esir.progmob.tp_android

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.io.File
import java.io.IOException
import java.io.InputStream

class TP2 : ComponentActivity() {
    private lateinit var fileListAdapter: CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tp2_layout1);
        /* Questions 1 à 4 */

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

        /* Question 5 à 7 */
        val nameFileView = findViewById<EditText>(R.id.form2)

        // Adapter for ListView
        val filesDir = applicationContext.filesDir
        val fileList = filesDir.listFiles()?.toList() ?: emptyList()
        val list = findViewById<ListView>(R.id.list)
        fileListAdapter = CustomAdapter(this, fileList, list)
        list.adapter = fileListAdapter

        val buttonOk2 = findViewById<Button>(R.id.ok2)
        buttonOk2.setOnClickListener {
            val nameFile = nameFileView.text.toString()
            Log.i("Nom du fichier", nameFile)
            if (nameFile == "") {
                AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Le nom du fichier est vide")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                // on crée un fichier avec le nom mis dans l'editView
                this.openFileOutput(nameFile, Context.MODE_PRIVATE) // /!\ createNewFile ne marche pas
                refreshFileList(list)
            }
        }

        // Faire une nouvelle classe Custom Adapter associé à un layout avec une zone de texte et un bouton. C'est cet adapter qu'on va donner à la liste
    }

    /**
     * Met à jour la liste des fichiers
     */
    protected fun refreshFileList(listView : ListView) {
        val filesDir = applicationContext.filesDir
        val fileList = filesDir.listFiles()?.toList() ?: emptyList()
        fileListAdapter = CustomAdapter(this, fileList, listView)
        listView.adapter = fileListAdapter
    }

    inner class CustomAdapter(context: Context, files: List<File>, listView: ListView) :
        ArrayAdapter<File>(context, 0, files) {

        private var listView: ListView = listView

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var itemView = convertView
            if (itemView == null) {
                // on convertit le code xml en objet View
                itemView = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
            }

            val file = getItem(position)
            val textViewListItem = itemView!!.findViewById<TextView>(R.id.textView5)
            val buttonListItem = itemView.findViewById<Button>(R.id.button5)

            // Set file name in TextView
            textViewListItem.text = file?.name ?: ""

            // Set click listener for the button
            buttonListItem.setOnClickListener {
                file?.let {
                    if (it.delete()) {
                        // File deleted successfully
                        // Refresh the file list
                        refreshFileList(listView)
                    }
                }
            }

            return itemView
        }
    }
}