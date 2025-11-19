package com.cs407.lab09

import android.hardware.Sensor
import android.hardware.SensorEvent
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BallViewModel : ViewModel() {

    private var ball: Ball? = null
    private var lastTimestamp: Long = 0L

    // Expose the ball's position as a StateFlow
    private val _ballPosition = MutableStateFlow(Offset.Zero)
    val ballPosition: StateFlow<Offset> = _ballPosition.asStateFlow()

    /**
     * Called by the UI when the game field's size is known.
     */
    fun initBall(fieldWidth: Float, fieldHeight: Float, ballSizePx: Float) {
        if (ball == null) {
            // Initializes the ball instance
            ball = Ball(fieldWidth, fieldHeight, ballSizePx)

            // Updates the StateFlow with the initial position
             _ballPosition.value = Offset(ball!!.posX, ball!!.posY)
        }
    }

    /**
     * Called by the SensorEventListener in the UI.
     */
    fun onSensorDataChanged(event: SensorEvent) {
        // Ensure ball is initialized
        val currentBall = ball ?: return

        if (event.sensor.type == Sensor.TYPE_GRAVITY) {
            if (lastTimestamp != 0L) {
                // Calculates the time difference (dT) in seconds
                val NS2S = 1.0f / 1000000000.0f
                val dT = (event.timestamp - lastTimestamp) * NS2S

                // Updates the ball's position and velocity
                val x: Float = -event.values[0]
                val y: Float = event.values[1] // Hint: The sensor's x and y-axis are inverted

                currentBall.updatePositionAndVelocity(xAcc = x, yAcc = y, dT = dT)

                // TODO: remove??? This is an additional check that was added (maybe needed)
                currentBall.checkBoundaries()

                // Updates the StateFlow to notify the UI
                _ballPosition.update { Offset(currentBall.posX, currentBall.posY) }
            }

            // Updates the lastTimestamp with the current timestamp of this action
            lastTimestamp = event.timestamp
        }
    }

    fun reset() {
        // Resets the ball's state
        val currentBall = ball ?: return

        ball?.reset()

        // Updates the StateFlow with the reset position
        ball?.let {
            _ballPosition.value = Offset(currentBall.posX, currentBall.posY)
        }

        // Resets the lastTimestamp
        lastTimestamp = 0L
    }
}