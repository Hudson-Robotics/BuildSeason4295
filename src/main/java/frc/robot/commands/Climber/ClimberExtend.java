// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.Command;

public class ClimberExtend extends Command {
  private final Climber climber;

  public ClimberExtend(Climber subsystem) {
    this.climber = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    climber.Extend();
  }

  @Override
  public void end(boolean interrupted)
  {
    climber.Stop();
  }
}
