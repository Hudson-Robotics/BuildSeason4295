// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.Interfaces.Motor;
import frc.robot.commands.Intake.IntakeStop;

public class Intake extends SubsystemBase {
  private final Motor intakeMotor;

  public Intake(Motor intakeMotor) {
    this.intakeMotor = intakeMotor;
    setDefaultCommand(new IntakeStop(this)); // ok with this for now
  }

  //These should be implemented in there own classes
  public void Stop() {
    intakeMotor.setSpeed(0);
  }

  public void Load() {
    intakeMotor.setSpeed(Speeds.kIntakeLoad);
  }

  public void Unload() {
    intakeMotor.setSpeed(Speeds.kIntakeUnload);
  }

  public void Reverse() {
    intakeMotor.setSpeed(-Speeds.kIntakeReverse);
  }

  //Could be use to write the speed
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
