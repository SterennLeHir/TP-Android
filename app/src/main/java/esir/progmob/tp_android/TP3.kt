package esir.progmob.tp_android

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat

class TP3 : ComponentActivity() { //GestureDetector.OnGestureListener pour les mouvements

    // Question 1 private lateinit var mDetector: GestureDetectorCompat

    // Question 2 private var mVelocityTracker: VelocityTracker? = null

    private lateinit var sensorManager: SensorManager

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
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceSensors.map{d -> d.name + " " + d.isWakeUpSensor});
        val list = findViewById<ListView>(R.id.list)
        list.adapter = adapter
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        Log.d("SENSOR", accelerometer?.minDelay.toString())
        val light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        Log.d("SENSOR", light?.)
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