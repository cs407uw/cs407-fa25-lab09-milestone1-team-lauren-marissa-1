package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        // TODO: Call reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        // storing prev acceleration values for a_0
        val prevAccX = accX
        val prevAccY = accY

        // saving/ updating acceleration values for a_1
        accX = xAcc
        accY = yAcc

        // when t = t_1 velocity equation below
        val newVelX = velocityX + 0.5f * (prevAccX + accX) * dT
        val newVelY = velocityY + 0.5f * (prevAccY + accY) * dT

        // distance traveled by the ball from t_0 to t_1
        val distX = velocityX * dT + (1f / 6f) * (dT * dT) * (3 * prevAccX + accX)
        val distY = velocityY * dT + (1f / 6f) * (dT * dT) * (3 * prevAccY + accY)

        // update the positions and velocities!
        posX += distX
        posY += distY
        velocityX = newVelX
        velocityY = newVelY

    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // TODO: implement the checkBoundaries function
        // (Check all 4 walls: left, right, top, bottom)
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // TODO: implement the reset function
        // (Reset posX, posY, velocityX, velocityY, accX, accY, isFirstUpdate)
    }
}