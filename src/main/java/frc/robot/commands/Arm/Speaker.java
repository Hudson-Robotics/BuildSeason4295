// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import frc.robot.subsystems.Arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Positions;

public class Speaker extends Command {
  private final Arm arm;

  public Speaker(Arm subsystem) {
    this.arm = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    arm.setArmPosition(Positions.kArmSpeaker);
  }

  @Override
  public void end(boolean interrupted)
  {
    
  }

  @Override
  public boolean isFinished()
  {
    double threshold = .01;
    double lowerBound = Positions.kArmSpeaker * (1 - threshold);
    double upperBound = Positions.kArmSpeaker * (1 + threshold);

    return lowerBound < arm.getArmPosition() && arm.getArmPosition() < upperBound;

  }
}
