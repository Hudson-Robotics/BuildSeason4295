// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Positions;

public class Reverse extends Command {
  private final Arm arm;

  public Reverse(Arm arm) {
    this.arm = arm;
    addRequirements(arm);
  }

  @Override
  public void initialize() {
    arm.setArmPosition(Positions.kArmReverse);
  }

  @Override
  public void end(boolean interrupted)
  {
  }
}
