package esir.progmob.tp_android

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.AlertDialog
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.getSystemService


class TP4 : ComponentActivity() {


    private var liste: MutableList<BluetoothDevice> = mutableListOf()
    private val REQUEST_ENABLE_BT: Int = 1 // doit être supérieur à 0
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d("BLUETOOTH", "On a reçu quelque chose")
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {
                        liste.add(device)
                        updateList()
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Log.d("BLUETOOTH", "Discovery started")
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d("BLUETOOTH", "Discovery finished")
                }
            }
        }
    }

    private var bluetoothAdapter: BluetoothAdapter? = null
    // Variables de permission
    private var isGrantedBluetooth: Boolean = false
    val requestPermissionLauncher =
        this.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                isGrantedBluetooth = true
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

    @SuppressLint("MissingPermission") // pas terrible, à corriger
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tp4_layout1)
        // On vérifie ou on demande à l'utilisateur s'il accepte la permission bluetooth
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) { // Si la permission n'est pas déjà accepté
            // Afficher une explication à l'utilisateur via une boîte de dialogue
            AlertDialog.Builder(this)
                .setTitle("BLUETOOTH CONNECT")
                .setMessage("This app requires Bluetooth permission to perform XYZ functionality. Please grant the permission to continue.")
                .setPositiveButton("OK") { _, _ ->
                    // Demander la permission une fois que l'utilisateur appuie sur OK
                    requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
                }
                .show()
        } else { // La permission BLUETOOTH_CONNECT est déjà accordée
            isGrantedBluetooth = true
        }
        if (this.isGrantedBluetooth) {
            // On obtient le bluetooth adapter
            bluetoothAdapter = getSystemService(BluetoothManager::class.java).adapter
            Log.d("BLUETOOTH", bluetoothAdapter!!.name)
            if (bluetoothAdapter == null) {
                // Device doesn't support Bluetooth
                Log.d("BLUETOOTH", "Pas de bluetooth adapter")
            } else { // On active le bluetooth s'il n'est pas activé
                val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                        putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
                    }
                Log.d("BLUETOOTH", "visibilité")
                startActivity(discoverableIntent)
            }

            // On enregistre un listener qui écoutent certains messages
            val filter = IntentFilter().apply {
                BluetoothAdapter.ACTION_DISCOVERY_STARTED
                BluetoothDevice.ACTION_FOUND
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED
            }
            registerReceiver(receiver, filter)

            Log.d("BLUETOOTH", "listener enregistré")
            // On recherche parmi les périphériques associés
            liste = bluetoothAdapter?.bondedDevices?.toMutableList() ?: mutableListOf<BluetoothDevice>()
            Log.d("BLUETOOTH", liste.toString())

            // Création de la liste avec les appareils apairés
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                liste.map { d -> d.name + "   " + d.address })
            val list = findViewById<ListView>(R.id.liste)
            list.adapter = adapter

            val start = bluetoothAdapter?.startDiscovery()
            Log.d("BLUETOOTH", start.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.isGrantedBluetooth) unregisterReceiver(receiver)
    }

    @SuppressLint("MissingPermission")
    fun updateList() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            liste.map { d -> d.name + "   " + d.address })
        val list = findViewById<ListView>(R.id.liste)
        list.adapter = adapter
    }
}