// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.Command;

public class ClimberRightClimb extends Command {
  private final Climber climber;

  public ClimberRightClimb(Climber subsystem) {
    this.climber = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    climber.RightClimb();
  }

  @Override
  public void end(boolean interrupted)
  {
    //climber.Stop(); //This is wrong... this will stop both
  }
}
