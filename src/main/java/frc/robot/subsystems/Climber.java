// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Interfaces.PIDMotor;

public class Climber extends SubsystemBase
{

  private final PIDMotor climberLeft;
  private final PIDMotor climberRight;

  public Climber(PIDMotor climberLeft, PIDMotor climberRight)
  {
    this.climberLeft = climberLeft;
    this.climberRight = climberRight;

    climberRight.setInverted(true);
  }

  public void setLeftClimbSpeed(double speed)
  {
    climberLeft.setSpeed(speed);
  }

  public void setRightClimbSpeed(double speed)
  {
    climberRight.setSpeed(speed);
  }

  public void setClimbSpeed(double speed)
  {
    this.setLeftClimbSpeed(speed);
    this.setRightClimbSpeed(speed);
  }


  @Override
  public void periodic()
  {
    SmartDashboard.putNumber("Left Climb Speed", climberLeft.getSpeed());
    SmartDashboard.putNumber("Right Climb Speed", climberRight.getSpeed());
  }

}
