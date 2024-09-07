package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.commands.ShooterStop;

public class Shooter extends SubsystemBase {
  private final TalonFX shooterMotor = new TalonFX(Ports.kShooter);

  public Shooter() {
    setDefaultCommand(new ShooterStop(this));
  }

  public void Shoot() {
    shooterMotor.set(-Speeds.kShooterShoot);
  }

  public void Stop() {
    shooterMotor.set(0);
  }

  public void Reverse() {
    shooterMotor.set(Speeds.kShooterReverse);
  }

  //public void 

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
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
