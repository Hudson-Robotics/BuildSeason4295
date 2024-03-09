
package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.DriveTrain.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
  /** Example static factory for an autonomous command. */
  // public static Command exampleAuto(ExampleSubsystem subsystem) {
  //   return Commands.sequence(subsystem.exampleMethodCommand(), new DriveCommand(subsystem));
  // }

  public static Command driveAuto(DriveTrain driveTrain) {
    return Commands.sequence(driveTrain.exampleMethodCommand(), new DriveDistance(driveTrain));
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
