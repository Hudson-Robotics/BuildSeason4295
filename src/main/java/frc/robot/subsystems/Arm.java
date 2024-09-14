package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.Interfaces.PIDMotor;
import frc.robot.commands.Arm.ArmStop;

public class Arm extends SubsystemBase {
  private final PIDMotor leftArmMotor;
  private final PIDMotor rightArmMotor;

  private SparkPIDController leftPID;
  private SparkPIDController rightPID;

  private final DigitalInput fullyBackSwitch = new DigitalInput(Ports.kBackSwitch);
  private final DigitalInput fullyFwdSwitch = new DigitalInput(Ports.kForwardSwitch);

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

    setDefaultCommand(new ArmStop(this));
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

  public void Stop() {
    leftArmMotor.setSpeed(0);
    rightArmMotor.setSpeed(0);
  }

  public void Forward() {
    leftPID.setReference(Positions.kArmForward, CANSparkMax.ControlType.kSmartMotion);
    rightPID.setReference(Positions.kArmForward, CANSparkMax.ControlType.kSmartMotion);
  }

  public void GuesstimateArmForSpeaker() {
    leftPID.setReference(7.8095, CANSparkMax.ControlType.kSmartMotion);
    rightPID.setReference(7.8095, CANSparkMax.ControlType.kSmartMotion);
  }

  public void Reverse() {
    leftPID.setReference(Positions.kArmReverse, CANSparkMax.ControlType.kSmartMotion);
    rightPID.setReference(Positions.kArmReverse, CANSparkMax.ControlType.kSmartMotion);
  }

  public boolean FullyForward() {
    return fullyFwdSwitch.get();
  }

  public boolean FullyBack() {
    return fullyBackSwitch.get();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Arm PIDMotors Encoder", leftArmMotor.getEncoderPosition());
    SmartDashboard.putNumber("Right Arm PIDMotors Encoder", rightArmMotor.getEncoderPosition());
  }

}
