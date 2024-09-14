package frc.robot;

import frc.robot.Constants.Ports;
import frc.robot.Implemented.SparkMaxBrushlessMotor;
import frc.robot.Implemented.TalonMotor;
import frc.robot.commands.Arm.ArmForward;
import frc.robot.commands.Arm.ArmGuesstimateSpeaker;
import frc.robot.commands.Arm.ArmReverse;
import frc.robot.commands.Climber.ClimberClimb;
import frc.robot.commands.Climber.ClimberExtend;
import frc.robot.commands.Climber.ClimberLeftClimb;
import frc.robot.commands.Climber.ClimberRightClimb;
import frc.robot.commands.Intake.IntakeLoad;
import frc.robot.commands.Intake.IntakeReverse;
import frc.robot.commands.Intake.IntakeUnload;
import frc.robot.commands.Shooter.ShooterReverse;
import frc.robot.commands.Shooter.ShooterShoot;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.Interfaces.Motor;
import frc.robot.Interfaces.PIDMotor;
import frc.robot.subsystems.DriveTrain.SwerveDrive;
import frc.robot.subsystems.DriveTrain.SwerveModule;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveDrive driveTrain;
  private final Climber climber;
  private final Arm arm;
  private final Intake intake;
  private final Shooter shooter;

  //Controllers
  private final CommandXboxController xboxDriveController;
  private final CommandXboxController xboxOperatorController;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    this.configureDriverController();
    this.configureOperatorController();
  }

  private void configureDriverController()
  {
    Trigger resetNavX = xboxDriveController.pov(180);
    Trigger retractLeft = xboxDriveController.leftTrigger(.5);
    Trigger retractRight = xboxDriveController.rightTrigger(.5);
    Trigger retract = xboxDriveController.rightBumper();
    Trigger extend = xboxDriveController.leftBumper();

    retract.whileTrue(new ClimberClimb(climber));
    retractLeft.whileTrue(new ClimberLeftClimb(climber));
    retractRight.whileTrue(new ClimberRightClimb(climber));
    extend.whileTrue(new ClimberExtend(climber));
    resetNavX.onTrue(new InstantCommand(() -> driveTrain.ResetNavX()));

    //Dont like it, but dont know why
    driveTrain.setDefaultCommand(
        new RunCommand(() -> driveTrain.drive(
            -xboxDriveController.getLeftY(),
            xboxDriveController.getLeftX(),
            -xboxDriveController.getRightX()), driveTrain));
  }

  private void configureOperatorController()
  {
    Trigger forward = xboxOperatorController.y();
    Trigger reverse = xboxOperatorController.a();
    Trigger intakeIn = xboxOperatorController.leftTrigger();
    Trigger shooterOut = xboxOperatorController.rightTrigger();
    Trigger intakeOut = xboxOperatorController.leftBumper();
    Trigger shooterIn = xboxOperatorController.rightBumper();
    Trigger setArmPostionForSpeaker = xboxOperatorController.x();
    Trigger IntakeUnload = xboxOperatorController.pov(180);

    intakeIn.whileTrue(new IntakeLoad((intake)));
    shooterOut.whileTrue(new ShooterReverse(shooter));
    intakeOut.whileTrue(new IntakeReverse(intake));
    shooterIn.whileTrue(new ShooterShoot(shooter));
    setArmPostionForSpeaker.onTrue(new ArmGuesstimateSpeaker(arm));
    forward.whileTrue(new ArmForward(arm));
    reverse.whileTrue(new ArmReverse(arm));
    IntakeUnload.whileTrue(new IntakeUnload(intake));
  }

  //wtf java... why you like this
  //Instance initializer block for DriveTrain
  {
    PIDMotor topLeftRotationMotor = new SparkMaxBrushlessMotor(Ports.kFrontLeftTurning, true);
    PIDMotor topLeftDriveMotor = new SparkMaxBrushlessMotor(Ports.kFrontLeftDrive, false);
    SwerveModule topLeftSwerveModule = new SwerveModule(topLeftRotationMotor, topLeftDriveMotor, "Front Left");

    PIDMotor topRightRotationMotor = new SparkMaxBrushlessMotor(Ports.kFrontRightTurning, true);
    PIDMotor topRightDriveMotor = new SparkMaxBrushlessMotor(Ports.kFrontRightDrive, false);
    SwerveModule topRightSwerveModule = new SwerveModule(topRightRotationMotor, topRightDriveMotor, "Front Right");

    PIDMotor backLeftRotationMotor = new SparkMaxBrushlessMotor(Ports.kBackLeftTurning, true);
    PIDMotor backLeftDriveMotor = new SparkMaxBrushlessMotor(Ports.kBackLeftDrive, false);
    SwerveModule backLeftSwerveModule = new SwerveModule(backLeftRotationMotor, backLeftDriveMotor, "Back Left");

    PIDMotor backRightRotationMotor = new SparkMaxBrushlessMotor(Ports.kBackRightTurning, true);
    PIDMotor backRightDriveMotor = new SparkMaxBrushlessMotor(Ports.kBackRightDrive, false);
    SwerveModule backRightSwerveModule = new SwerveModule(backRightRotationMotor, backRightDriveMotor, "Back Right");

    this.driveTrain = new SwerveDrive(topLeftSwerveModule, topRightSwerveModule, backLeftSwerveModule, backRightSwerveModule);
  }

  //Instance initializer block for Climber
  {
    PIDMotor leftClimberMotor = new SparkMaxBrushlessMotor(Ports.kClimberLeft, false);
    PIDMotor rightClimberMotor = new SparkMaxBrushlessMotor(Ports.kClimberRight, false);
    this.climber = new Climber(leftClimberMotor, rightClimberMotor);
  }

  //Instance initializer block for Arm
  {
    PIDMotor leftArmMotor = new SparkMaxBrushlessMotor(Ports.kArmLeft, false);
    PIDMotor rightArmMotor = new SparkMaxBrushlessMotor(Ports.kArmRight, false);
    this.arm = new Arm(leftArmMotor, rightArmMotor);
  }

  //Instance initializer block for Intake
  {
    Motor intakeMotor = new TalonMotor(Ports.kIntake);
    this.intake = new Intake(intakeMotor);
  }
  
  //Instance initializer block for Shooter
  {
    Motor shooterMotor = new TalonMotor(Ports.kShooter);
    this.shooter = new Shooter(shooterMotor);
  }

  //Instance initializer block for Controllers
  {
      this.xboxDriveController = new CommandXboxController(Ports.kDriverControllerPort);
      this.xboxOperatorController = new CommandXboxController(Ports.kOperatorControlPort);
  }

  public Command getAutonomousCommand() {
    return new RunCommand(() -> driveTrain.drive(
        -.15,
        -.05,
        0), driveTrain)
        .withTimeout(5);
  }
}
