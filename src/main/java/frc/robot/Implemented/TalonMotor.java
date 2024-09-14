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

    //TODO: This is bad design, will fix it later
    @Override
    public double getEncoderPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEncoderPosition'");
    }

    @Override
    public double getEncoderSpeed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEncoderSpeed'");
    }

    @Override
    public void setEncoderPosition(double position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEncoderPosition'");
    }

    @Override
    public void setInverted(boolean isInverted) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInverted'");
    }

    @Override
    public void restoreFactoryDefaults() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restoreFactoryDefaults'");
    }

    @Override
    public SparkPIDController getPIDController() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPIDController'");
    }
}
