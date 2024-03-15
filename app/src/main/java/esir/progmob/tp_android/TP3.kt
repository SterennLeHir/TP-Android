package esir.progmob.tp_android

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.VelocityTracker
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat

class TP3 : ComponentActivity(), GestureDetector.OnGestureListener {

    // Question 2 private lateinit var mDetector: GestureDetectorCompat

    private var mVelocityTracker: VelocityTracker? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tp3_layout1);
        /* Question 2
        mDetector = GestureDetectorCompat(this, this)
        mDetector.setIsLongpressEnabled(true)
        val ecran = findViewById<ConstraintLayout>(R.id.ecran)
        ecran.setOnTouchListener { v, event ->
            Log.i("LISTENER", event.toString())
            mDetector.onTouchEvent(event)
        }
    */
    }
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
                    Log.d("", "X velocity: ${getXVelocity(pointerId)}")
                    Log.d("", "Y velocity: ${getYVelocity(pointerId)}")
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // Return a VelocityTracker object back to be re-used by others.
                mVelocityTracker?.recycle()
                mVelocityTracker = null
            }
        }
        return true
    }
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
    }
}