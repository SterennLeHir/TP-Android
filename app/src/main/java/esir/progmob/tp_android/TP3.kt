package esir.progmob.tp_android

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlin.math.abs

class TP3 : ComponentActivity(),
    SensorEventListener { //GestureDetector.OnGestureListener pour les mouvements

    // Question 1 private lateinit var mDetector: GestureDetectorCompat

    // Question 2 private var mVelocityTracker: VelocityTracker? = null

    private lateinit var sensorManager: SensorManager
    private var light: Sensor? = null
    private var linear_acceleration: Sensor? = null

    //attributs acceleration
    private var lastAcceleration: FloatArray = floatArrayOf(0f, 0f, 0f)
    private var accelerationDelta: Float = 0f
    private var accelerationThreshold: Float = 15f
    private var lastUpdate: Long = 0
    private var counter: Int = 0
    private var lastShake : Long = 0

    //vibrator
    private var vibrator: Vibrator? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tp3_layout1)
        /* Question 1
        mDetector = GestureDetectorCompat(this, this)
        mDetector.setIsLongpressEnabled(true)
        val ecran = findViewById<ConstraintLayout>(R.id.ecran)
        ecran.setOnTouchListener { v, event ->
            Log.i("LISTENER", event.toString())
            mDetector.onTouchEvent(event)
        }
        */

        //Question capteurs
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            deviceSensors.map { d -> d.name + " " + d.isWakeUpSensor });
        val list = findViewById<ListView>(R.id.list)
        list.adapter = adapter
        linear_acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        Log.d("SENSOR", linear_acceleration?.minDelay.toString())
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // vibrateur
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lightValue = event.values[0]
            Log.d("SENSOR", lightValue.toString())
        } else if (event?.sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val curTime = System.currentTimeMillis()
            if (curTime - lastUpdate > 100) { // fréquence d'échantillonage
                val acceleration = event.values.clone()
                accelerationDelta = abs(acceleration[0] - lastAcceleration[0])
                val sign = acceleration[0] * lastAcceleration[0] //detection changement de sens

                Log.d("SENSOR", acceleration[0].toString() + " " + counter)
                if (accelerationDelta > accelerationThreshold && sign < 0) { // detecte une secousse
                    if(curTime - lastShake < 500){ // si on a shake il n'y a pas longtemps
                        // secousse detectée
                        counter++
                        if (counter >= 3) { // le téléphone a vraiment été secoué
                            counter = 0
                            Toast.makeText(this, "AAAAAAAAAAAAAAAAAAAAAAAAAAAh!", Toast.LENGTH_SHORT).show()
                            vibrator?.vibrate(1000)
                        }
                    }
                    else{
                        counter = 0 //timeout du dernier shake
                    }
                    lastShake = curTime
                }
                lastAcceleration = acceleration // valeur de la dernière accéleration
            }
            lastUpdate = curTime // temps de la dernière accélération
        }
    }

    override fun onResume() {
        super.onResume()
        // Register sensors
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, linear_acceleration, SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onPause() {
        super.onPause()
        // Unregister all listeners
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //do nothing
    }
    /* Question 2
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                // Reset the velocity tracker back to its initial state.
                mVelocityTracker?.clear()
                // If necessary, retrieve a new VelocityTracker object to watch
                // the velocity of a motion.
                mVelocityTracker = mVelocityTracker ?: VelocityTracker.obtain()
                // Add a user's movement to the tracker.
                mVelocityTracker?.addMovement(event)
            }
            MotionEvent.ACTION_MOVE -> {
                mVelocityTracker?.apply {
                    val pointerId: Int = event.getPointerId(event.actionIndex)
                    addMovement(event)
                    // When you want to determine the velocity, call
                    // computeCurrentVelocity(). Then, call getXVelocity() and
                    // getYVelocity() to retrieve the velocity for each pointer
                    // ID.
                    computeCurrentVelocity(1000)
                    // Log velocity of pixels per second. It's best practice to
                    // use VelocityTrackerCompat where possible.
                    Log.d("Velo", "X velocity: ${getXVelocity(pointerId)}")
                    Log.d("Velo", "Y velocity: ${getYVelocity(pointerId)}")
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // Return a VelocityTracker object back to be re-used by others.
                mVelocityTracker?.recycle()
                mVelocityTracker = null
            }
        }
        return true
    }*/

    /* Question 1
    override fun onDown(e: MotionEvent): Boolean {
        Log.d("TOUCH", e.toString())
        val ecran = findViewById<ConstraintLayout>(R.id.ecran)
        ecran.setBackgroundColor(Color.parseColor("#A52448"))
        return true
    }

    override fun onShowPress(e: MotionEvent) {

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {

        return true
    }

    override fun onLongPress(e: MotionEvent) {
        Log.d("TOUCH", e.toString())
        val ecran = findViewById<ConstraintLayout>(R.id.ecran)
        ecran.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val ecran = findViewById<ConstraintLayout>(R.id.ecran)
        ecran.setBackgroundColor(Color.parseColor("#1222FF"))
        return true
    } */

}