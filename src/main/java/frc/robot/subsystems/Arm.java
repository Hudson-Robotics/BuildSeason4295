package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Interfaces.PIDMotor;

public class Arm extends SubsystemBase {
  private final PIDMotor leftArmMotor;
  private final PIDMotor rightArmMotor;

  private SparkPIDController leftPID;
  private SparkPIDController rightPID;

  public Arm(PIDMotor leftMotor, PIDMotor rightMotor) {
    this.leftArmMotor = leftMotor;
    this.rightArmMotor = rightMotor;
    
    //This might not be needed - will need to test it
    leftArmMotor.restoreFactoryDefaults();
    rightArmMotor.restoreFactoryDefaults();

    rightArmMotor.setInverted(true);

    leftPID = leftArmMotor.getPIDController();
    rightPID = rightArmMotor.getPIDController();

    ConfigurePID(leftPID);
    ConfigurePID(rightPID);
  }

  private void ConfigurePID(SparkPIDController PID) {
    PID.setP(5e-5);
    PID.setI(1e-6);
    PID.setD(0);
    PID.setIZone(0);
    PID.setFF(0.00156);
    //PID.setFF(0.000156);
    PID.setOutputRange(-0.3, 0.5);

    int smartMotionSlot = 0;
    PID.setSmartMotionMaxVelocity(3000, smartMotionSlot);
    PID.setSmartMotionMinOutputVelocity(0, smartMotionSlot);
    PID.setSmartMotionMaxAccel(1000, smartMotionSlot);
    PID.setSmartMotionAllowedClosedLoopError(0, smartMotionSlot);
  }

  public void setArmPosition(double position)
  {
    leftPID.setReference(position, CANSparkMax.ControlType.kSmartMotion);
    rightPID.setReference(position, CANSparkMax.ControlType.kSmartMotion);
  }

  public double getArmPosition()
  {
    return (leftArmMotor.getEncoderPosition() + leftArmMotor.getEncoderPosition()) / 2;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Arm PIDMotors Encoder", leftArmMotor.getEncoderPosition());
    SmartDashboard.putNumber("Right Arm PIDMotors Encoder", leftArmMotor.getEncoderPosition());
  }

}
