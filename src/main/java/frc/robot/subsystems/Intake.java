// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Interfaces.Motor;

public class Intake extends SubsystemBase {
  private final Motor intakeMotor;

  public Intake(Motor intakeMotor) {
    this.intakeMotor = intakeMotor;
  }

  public void setIntakeSpeed(double speed)
  {
    intakeMotor.setSpeed(speed);
  }

  @Override
  public void periodic()
  {
    SmartDashboard.putNumber("Intake Speed", intakeMotor.getSpeed());
  }

}
