package jp.ac.titech.itpro.sdl.resist

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var rotationView: RotationView? = null
    private var manager: SensorManager? = null
    private var gyroscope: Sensor? = null

    private val NS2S = 1.0f / 1000000000.0f
    private var timestamp: Long = 0
    private var currentRad: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        rotationView = findViewById(R.id.rotation_view)
        manager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (manager == null) {
            Toast.makeText(this, R.string.toast_no_sensor_manager, Toast.LENGTH_LONG).show()
            finish()
            return
        }
        gyroscope = manager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroscope == null) {
            Toast.makeText(this, R.string.toast_no_gyroscope, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        manager!!.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        manager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val omegaZ = event.values[2] // z-axis angular velocity (rad/sec)

        if (timestamp > 0) {
            val dT = (event.timestamp - timestamp) * NS2S
            currentRad += omegaZ * dT
        }
        rotationView!!.setDirection(currentRad)

        timestamp = event.timestamp
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        Log.d(TAG, "onAccuracyChanged: accuracy=$accuracy")
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
