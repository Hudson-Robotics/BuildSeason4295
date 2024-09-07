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
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
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
    // Trigger retract = xboxOperatorController.b(); //.rightBumper
    // Trigger extend = xboxOperatorController.x(); //.leftBumper
    Trigger resetNavX = xboxDriveController.pov(180);//xboxDriveController.leftBumper().and(xboxDriveController.rightBumper());
    Trigger retractLeft = xboxDriveController.leftTrigger(.5);
    Trigger retractRight = xboxDriveController.rightTrigger(.5);
    Trigger retract = xboxDriveController.rightBumper();
    Trigger extend = xboxDriveController.leftBumper();

    // Trigger load = xboxOperatorController.rightBumper(); //.b
    // Trigger shoot = xboxOperatorController.leftBumper(); //.x
    Trigger forward = xboxOperatorController.y();
    Trigger reverse = xboxOperatorController.a();

    Trigger intakeIn = xboxOperatorController.leftTrigger();
    Trigger shooterOut = xboxOperatorController.rightTrigger();//pov(90);
    intakeIn.whileTrue(new IntakeLoad((intake)));
    shooterOut.whileTrue(new ShooterReverse(shooter));

    Trigger intakeOut = xboxOperatorController.leftBumper();
    Trigger shooterIn = xboxOperatorController.rightBumper();
    intakeOut.whileTrue(new IntakeReverse(intake));
    shooterIn.whileTrue(new ShooterShoot(shooter));

    Trigger setArmPostionForSpeaker = xboxOperatorController.x();
    setArmPostionForSpeaker.onTrue(new ArmGuesstimateSpeaker(arm));

    // Trigger ShooterReverse = xboxOperatorController.leftTrigger();//pov(270);
    Trigger IntakeUnload = xboxOperatorController.pov(180);

    retract.whileTrue(new ClimberClimb(climber));
    retractLeft.whileTrue(new ClimberLeftClimb(climber));
    retractRight.whileTrue(new ClimberRightClimb(climber));
    extend.whileTrue(new ClimberExtend(climber));
    resetNavX.onTrue(new InstantCommand(() -> driveTrain.ResetNavX()));

    forward.whileTrue(new ArmForward(arm));
    reverse.whileTrue(new ArmReverse(arm));

    // load.whileTrue(new IntakeLoad(intake));
    // intakeReverse.whileTrue(new IntakeReverse(intake));//.whileTrue(new
    IntakeUnload.whileTrue(new IntakeUnload(intake));
    
    // shoot.whileTrue(new ShooterShoot(shooter));
    // ShooterReverse.whileTrue(new ShooterReverse(shooter));

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
