package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.Interfaces.Motor;
import frc.robot.commands.Shooter.ShooterStop;

public class Shooter extends SubsystemBase {
  private final Motor shooterMotor;

  public Shooter(Motor shooterMotor) {
    this.shooterMotor = shooterMotor;
    setDefaultCommand(new ShooterStop(this));
  }

  public void Shoot() {
    shooterMotor.setSpeed(-Speeds.kShooterShoot);
  }

  public void Stop() {
    shooterMotor.setSpeed(0);
  }

  public void Reverse() {
    shooterMotor.setSpeed(Speeds.kShooterReverse);
  }

  //maybe use this to write to dashboard
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
