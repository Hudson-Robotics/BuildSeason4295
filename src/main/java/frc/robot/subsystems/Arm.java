package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.commands.ArmStop;
import frc.robot.commands.Rumble;

public class Arm extends SubsystemBase {
  private final CANSparkMax leftArmMotor = new CANSparkMax(Ports.kArmLeft, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax rightArmMotor = new CANSparkMax(Ports.kArmRight, CANSparkMax.MotorType.kBrushless);

  private RelativeEncoder leftMotorEncoder;
  private RelativeEncoder rightMotorEncoder;

  private SparkPIDController leftPID;
  private SparkPIDController rightPID;

  private final DigitalInput fullyBackSwitch = new DigitalInput(Ports.kBackSwitch);
  private final DigitalInput fullyFwdSwitch = new DigitalInput(Ports.kForwardSwitch);

  public Arm() {
    leftArmMotor.restoreFactoryDefaults();
    rightArmMotor.restoreFactoryDefaults();

    rightArmMotor.setInverted(true);

    leftMotorEncoder = leftArmMotor.getEncoder();
    rightMotorEncoder = rightArmMotor.getEncoder();

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
    // PID.setFF(0.000156);
    PID.setOutputRange(-0.3, 0.3);

    int smartMotionSlot = 0;
    PID.setSmartMotionMaxVelocity(3000, smartMotionSlot);
    PID.setSmartMotionMinOutputVelocity(0, smartMotionSlot);
    PID.setSmartMotionMaxAccel(1000, smartMotionSlot);
    PID.setSmartMotionAllowedClosedLoopError(0, smartMotionSlot);
  }

  public void Stop() {
    leftArmMotor.set(0);
    rightArmMotor.set(0);
  }

  public void Forward() {
    // if (FullyForward()) {
    // leftArmMotor.set(-Speeds.kArmForward);
    // rightArmMotor.set(-Speeds.kArmForward);
    // } else {
    // Rumble rumble = new Rumble(1, 1);
    // }
    leftPID.setReference(Positions.kArmForward, CANSparkMax.ControlType.kSmartMotion);
    rightPID.setReference(Positions.kArmForward, CANSparkMax.ControlType.kSmartMotion);
  }

  public void Reverse() {
    // if (FullyBack()) {
    // leftArmMotor.set(Speeds.kArmReverse);
    // rightArmMotor.set(Speeds.kArmReverse);
    // } else {
    // Rumble rumble = new Rumble(1, 1);
    // }
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
    SmartDashboard.putNumber("Left Arm Motor Encoder", leftMotorEncoder.getPosition());
    SmartDashboard.putNumber("Right Arm Motor Encoder", rightMotorEncoder.getPosition());
  }

}
