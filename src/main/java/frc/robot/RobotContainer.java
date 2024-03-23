package frc.robot;

import frc.robot.Constants.Ports;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.DriveTrain.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Climber climber = new Climber();
  private final DriveTrain driveTrain = new DriveTrain();
  // private final Shooter shooter = new Shooter();
  // private final Intake intake = new Intake();
  private final Arm arm = new Arm();

  public final static CommandXboxController xboxDriveController = new CommandXboxController(
      Ports.kDriverControllerPort);
  public final static CommandXboxController xboxOperatorController = new CommandXboxController(
      Ports.kOperatorControlPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    driveTrain.setDefaultCommand(
        new RunCommand(() -> driveTrain.drive(
            -xboxDriveController.getLeftY(),
            xboxDriveController.getLeftX(),
            -xboxDriveController.getRightX(),
            true), driveTrain));

    configureBindings();
  }

  private void configureBindings() {
    Trigger retract = xboxOperatorController.rightBumper();
    Trigger extend = xboxOperatorController.leftBumper();
    Trigger resetNavX = xboxDriveController.leftBumper().and(xboxDriveController.rightBumper());
    Trigger retractLeft = xboxOperatorController.leftTrigger(.5);
    Trigger retractRight = xboxOperatorController.rightTrigger(.5);

    Trigger load = xboxOperatorController.b();
    Trigger shoot = xboxOperatorController.x();
    Trigger forward = xboxOperatorController.y();
    Trigger reverse = xboxOperatorController.a();
    Trigger intakeReverse = xboxOperatorController.pov(180);

    retract.whileTrue(new ClimberClimb(climber));
    retractLeft.whileTrue(new ClimberLeftClimb(climber));
    retractRight.whileTrue(new ClimberRightClimb(climber));
    extend.whileTrue(new ClimberExtend(climber));
    resetNavX.onTrue(new InstantCommand(() -> driveTrain.ResetNavX()));
    // load.whileTrue(new IntakeLoad(intake));
    // shoot.whileTrue(new ShooterShoot(shooter)).whileTrue(new
    // IntakeUnload(intake));
    forward.whileTrue(new ArmForward(arm));
    reverse.whileTrue(new ArmReverse(arm));
    // intakeReverse.whileTrue(new IntakeReverse(intake)).whileTrue(new
    // ShooterReverse(shooter));

  }

  public Command getAutonomousCommand() {
    return new RunCommand(() -> driveTrain.drive(
        -.15,
        -.05,
        0,
        true), driveTrain)
        .withTimeout(5);
  }

  // public Command getAutonomousCommand() {
  // return new ParallelCommandGroup(new ShooterShoot(shooter), new
  // IntakeUnload(intake)).withTimeout(5);
  // // return new RunCommand(new ShooterShoot(shooter)).alongWith( new
  // // IntakeUnload(intake)).withTimeout(5) ;
  // // return new SequentialCommandGroup(
  // // return new RunCommand(() ->new ParallelCommandGroup(
  // // new RunCommand(() -> new ShooterShoot(shooter)),
  // // new RunCommand(() -> new IntakeUnload(intake)))); //,
  // // // new RunCommand(() -> driveTrain.drive(
  // // -0.1,
  // // 0,
  // // 0,
  // // false), driveTrain))
  // // .withTimeout(5);
  // }

}
