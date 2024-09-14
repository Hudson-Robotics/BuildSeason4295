// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Speeds;

public class LeftClimb extends Command {
  private final Climber climber;

  public LeftClimb(Climber climber) {
    this.climber = climber;
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    climber.setLeftClimbSpeed(Speeds.kClimberClimb);
  }

  @Override
  public void end(boolean interrupted)
  {
    climber.setLeftClimbSpeed(0);
  }
}
