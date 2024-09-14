// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
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
    //setDefaultCommand(new ClimberStop(this)); is this needed, might need to test before I remove it
  }

  // These are all Commands, so should we just have commands here
  // OR Should the Subsystem be only composed of parts (IE... PIDMotors and stuff) and leave the command in the commands file
  public void Stop()
  {
    climberLeft.setSpeed(0);
    climberRight.setSpeed(0);
  }

  public void Climb()
  {
    climberLeft.setSpeed(Speeds.kClimberClimb);
    climberRight.setSpeed(Speeds.kClimberClimb);
  }

  public void RightClimb()
  {
     climberRight.setSpeed(Speeds.kClimberClimb);
  }

  public void LeftClimb()
  {
    climberLeft.setSpeed(Speeds.kClimberClimb);
  }

  public void Extend()
  {
    climberLeft.setSpeed(-Speeds.kClimberExtend);
    climberRight.setSpeed(-Speeds.kClimberExtend);
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }

}
