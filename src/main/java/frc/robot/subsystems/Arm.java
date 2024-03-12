package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Ports;
import frc.robot.commands.ArmStop;

public class Arm extends SubsystemBase {
  private final CANSparkMax leftArmMotor = new CANSparkMax(Ports.kArmLeft, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax rightArmMotor = new CANSparkMax(Ports.kArmRight, CANSparkMax.MotorType.kBrushless);

  public Arm() {
    rightArmMotor.setInverted(true);
    setDefaultCommand(new ArmStop(this));
  }

  public void Stop() {
    leftArmMotor.set(0);
    rightArmMotor.set(0);
  }

  public void Forward() {
    leftArmMotor.set(0.1);
    rightArmMotor.set(0.1);
  }

  public void Reverse() {
    leftArmMotor.set(-0.1);
    rightArmMotor.set(-0.1);
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a
   * digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
