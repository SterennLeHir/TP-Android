package esir.progmob.tp_android

import android.app.AlertDialog
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main);

        // Récupération de la ListView
        val listView = findViewById<ListView>(R.id.list1)

        // Création de la map
        val map = hashMapOf<String,Int>("Morbihan - Bretagne" to 764161,
            "Ille-et-Vilaine - Bretagne" to 1088855,
            "Finistère - Bretagne" to 917179,
            "Côtes d'Armor - Bretagne" to 603640)

        // Création de la liste des éléments à afficher
        val items = map.keys.toTypedArray()

        // Création de l'adaptateur pour la ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items);

        // Liaison de l'adaptateur à la ListView
        listView.adapter = adapter;

        listView.setOnItemClickListener(OnItemClickListener { parent, view, position, id -> //on récupère la HashMap contenant les infos de notre item (titre, description, img)
            val key = parent.getItemAtPosition(position) as String

            // Utilisez la clé pour obtenir la valeur associée dans la HashMap
            val value = map[key]

            // Faites quelque chose avec la valeur (par exemple, affichez-la dans une boîte de dialogue)
            // Exemple : Affichage de la valeur dans une boîte de dialogue
            AlertDialog.Builder(this)
                .setTitle("Nombre d'habitants en $key")
                .setMessage("données de 2020 : $value")
                .setPositiveButton("OK", null)
                .show()
        })
        /*setContent {
            TPAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }*/
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello, my name is $name!",
        modifier = modifier
    )
}