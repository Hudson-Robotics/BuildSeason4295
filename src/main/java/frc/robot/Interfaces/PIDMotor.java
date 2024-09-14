package frc.robot.Interfaces;

import com.revrobotics.SparkPIDController;

//TODO: This is only the beginning, planning on fixing this Interface
public interface PIDMotor extends Motor
{
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
