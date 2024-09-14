// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Speeds;

public class LoadIn extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake intake;

  public LoadIn(Intake subsystem) {
    this.intake = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    intake.setIntakeSpeed(Speeds.kIntakeLoad);
  }

  @Override
  public void end(boolean interrupted)
  {
    intake.setIntakeSpeed(0);
  }
}
