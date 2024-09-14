package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Interfaces.Motor;

public class Shooter extends SubsystemBase {
  private final Motor shooterMotor;

  public Shooter(Motor shooterMotor) {
    this.shooterMotor = shooterMotor;
  }

  public void setShooterSpeed(double speed)
  {
    this.shooterMotor.setSpeed(speed);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter Speed", this.shooterMotor.getSpeed());
  }

}
