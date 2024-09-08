// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.Command;

public class ArmReverse extends Command {
  private final Arm arm;

  public ArmReverse(Arm subsystem) {
    this.arm = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    arm.Reverse();
  }

  @Override
  public void end(boolean interrupted)
  {
    // This could be updated to set the final encoder postion so the arm doesnt keep falling
    arm.Stop();
  }
}
