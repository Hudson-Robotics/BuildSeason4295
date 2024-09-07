package frc.robot.Interfaces;

/**
 * This is an Interface for Motors, to know what a Motor know can do
 * without knowing what type of motor controller being used
 */

public interface Motor
{
    /**
     * Get Speed of Motor
     */
    void setSpeed(double speed);

    /**
     * Set Speed of Motor
     */
    double getSpeed();

    /**
     * Get Position using Encoders
     */
    double getEncoderPosition();

    double getEncoderSpeed();

    void setEncoderPosition(double position);
}
