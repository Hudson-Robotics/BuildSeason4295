// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.DriveTrain.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class DriveDistance extends Command {
 // @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveTrain driveTrain;

double distance = 0;

  public DriveDistance(DriveTrain subsystem,double inches) {
    driveTrain = subsystem;
    distance = inches;
    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    driveTrain.ResetDistance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return driveTrain.GetDistance() >= distance;
  }
}
