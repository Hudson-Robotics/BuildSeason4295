// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Speeds;

public class Shoot extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter shooter;
  private Timer timer;

  public Shoot(Shooter shooter) {
    this.shooter = shooter;
    addRequirements(shooter);
    timer.reset();
    timer.start();
  }

  @Override
  public void initialize() {
    shooter.setShooterSpeed(Speeds.kShooterShoot);
  }

  @Override
  public void end(boolean interrupted)
  {
    shooter.setShooterSpeed(0);
  }

  @Override
  public boolean isFinished()
  {
    int timeLimitInSeconds = 2;
    return timer.hasElapsed(timeLimitInSeconds);
  }
}
