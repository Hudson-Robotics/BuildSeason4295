// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DriveTrain;

import frc.robot.subsystems.DriveTrain.SwerveDrive;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveStraight extends Command {
  private final SwerveDrive driveTrain;

  public DriveStraight(SwerveDrive driveTrain) {
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);
  }

  //These might need some speeds
  @Override
  public void initialize()
  {
    driveTrain.drive(.1, 0, 0);
  }

  @Override
  public void end(boolean interrupted)
  {
    driveTrain.drive(0, 0, .1);
  }
}
