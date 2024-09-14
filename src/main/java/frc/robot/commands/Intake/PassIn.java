// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Speeds;

public class PassIn extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake intake;
  private Timer timer;

  public PassIn(Intake intake) {
    this.intake = intake;
    timer = new Timer();
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    intake.setIntakeSpeed(Speeds.kIntakeUnload);
    timer.reset();
    timer.start();
  }

  @Override
  public void end(boolean interrupted)
  {
    intake.setIntakeSpeed(Speeds.kIntakeUnload);
  }

  @Override
  public boolean isFinished()
  {
    int timeLimitInSeconds = 1;
    return timer.hasElapsed(timeLimitInSeconds);
  }
}
