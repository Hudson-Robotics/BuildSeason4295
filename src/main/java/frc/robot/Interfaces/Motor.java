package frc.robot.Interfaces;
import com.revrobotics.SparkPIDController;
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

    void setInverted(boolean isInverted);

    // restore function, might be a sparkMax exclusive - need to plan it better
    void restoreFactoryDefaults();

    // getPIDController is a SparkMax Only
    // Dont like this, might implemente a general PIDController
    SparkPIDController getPIDController();
}
