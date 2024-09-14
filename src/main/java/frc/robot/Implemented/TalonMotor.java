package frc.robot.Implemented;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.SparkPIDController;

import frc.robot.Interfaces.Motor;

public class TalonMotor implements Motor
{
    private final TalonFX motor;
    
    public TalonMotor(int deviceID)
    {
        motor = new TalonFX(deviceID);
    }

    @Override
    public void setSpeed(double speed)
    {
        motor.set(speed);
    }

    @Override
    public double getSpeed()
    {
        return motor.get();
    }
}
