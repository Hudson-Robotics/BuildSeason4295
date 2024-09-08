// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.Command;

public class ArmGuesstimateSpeaker extends Command {
  private final Arm arm;

  public ArmGuesstimateSpeaker(Arm subsystem) {
    this.arm = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    arm.GuesstimateArmForSpeaker();
  }

  @Override
  public void end(boolean interrupted)
  {
    arm.Stop();
  }
}
