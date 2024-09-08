package frc.robot.Implemented;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkMaxAlternateEncoder.Type;
import frc.robot.Interfaces.Motor;

public class SparkMaxBrushlessMotor implements Motor
{
    final private CANSparkMax motor;
    final private RelativeEncoder encoder;

    public SparkMaxBrushlessMotor(int canBusAddress, boolean useAlternateEncoder)
    {
        motor = new CANSparkMax(canBusAddress, MotorType.kBrushless);
        encoder = useAlternateEncoder ? motor.getAlternateEncoder(Type.kQuadrature, 4096) : motor.getEncoder();
    }

    @Override
    public void setSpeed(double speed) {
        motor.set(speed);
    }

    @Override
    public double getSpeed() {
        return motor.get();
    }

    @Override
    public double getEncoderPosition() {
        return encoder.getPosition();
    }

    @Override
    public double getEncoderSpeed()
    {
        return encoder.getVelocity();
    }

    @Override
    public void setEncoderPosition(double position)
    {
        encoder.setPosition(position);
    }

    @Override
    public void setInverted(boolean setInverted)
    {
        motor.setInverted(setInverted);
    }
}
