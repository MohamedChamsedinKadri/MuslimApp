package com.example.firstapp.ui.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.input.key.type

class Compass(context: Context) {

    interface CompassListener {
        fun onNewAzimuth(azimuthValue: Float)
    }

    private var listener: CompassListener? = null
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magneticFieldSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private var accelerometerValues = FloatArray(3)
    private var magneticFieldValues = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    private val sensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> accelerometerValues = event.values.clone()
                Sensor.TYPE_MAGNETIC_FIELD -> magneticFieldValues = event.values.clone()
            }

            SensorManager.getRotationMatrix(
                rotationMatrix,
                null,
                accelerometerValues,
                magneticFieldValues
            )
            SensorManager.getOrientation(rotationMatrix, orientationAngles)

            val azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
            listener?.onNewAzimuth(azimuth)
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // Handle accuracy changes if needed
        }
    }

    fun setListener(l: CompassListener) {
        listener = l
    }

    fun start() {
        accelerometerSensor?.let {
            sensorManager.registerListener(
                sensorEventListener,
                it,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
        magneticFieldSensor?.let {
            sensorManager.registerListener(
                sensorEventListener,
                it,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }

    fun stop() {
        sensorManager.unregisterListener(sensorEventListener)
    }
}