package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Ports;
import frc.robot.commands.ArmStop;
import frc.robot.commands.Rumble;

public class Arm extends SubsystemBase {
  private final CANSparkMax leftArmMotor = new CANSparkMax(Ports.kArmLeft, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax rightArmMotor = new CANSparkMax(Ports.kArmRight, CANSparkMax.MotorType.kBrushless);

  private final DigitalInput fullyBackSwitch = new DigitalInput(Ports.kBackSwitch);
  private final DigitalInput fullyFwdSwitch = new DigitalInput(Ports.kForwardSwitch);

  public Arm() {
    rightArmMotor.setInverted(true);
    setDefaultCommand(new ArmStop(this));
  }

  public void Stop() {
    leftArmMotor.set(0);
    rightArmMotor.set(0);
  }

  public void Forward() {
    if (!FullyForward()) {
      leftArmMotor.set(0.1);
      rightArmMotor.set(0.1);
    } else {
      Rumble rumble = new Rumble(1, 1);
    }
  }

  public void Reverse() {
    if (!FullyBack()) {
      leftArmMotor.set(-0.1);
      rightArmMotor.set(-0.1);
    } else {
      Rumble rumble = new Rumble(1, 1);
    }
  }

  public boolean FullyForward() {
    return fullyFwdSwitch.get();
  }

  public boolean FullyBack() {
    return fullyBackSwitch.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
