package frc.robot;

import frc.robot.Constants.Ports;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.DriveTrain.DriveTrain;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Climber climber = new Climber();
  private final DriveTrain driveTrain = new DriveTrain();
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Arm arm = new Arm();

  public final static CommandXboxController xboxController = new CommandXboxController(
      Ports.kDriverControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    driveTrain.setDefaultCommand(
        new RunCommand(() -> driveTrain.drive(
            -xboxController.getLeftY(),
            xboxController.getLeftX(),
            -xboxController.getRightX(),
            false), driveTrain));

    configureBindings();
  }

  private void configureBindings() {
    Trigger retract = xboxController.rightBumper();
    Trigger extend = xboxController.leftBumper();
    Trigger load = xboxController.b();
    Trigger shoot = xboxController.x();
    Trigger forward = xboxController.y();
    Trigger reverse = xboxController.a();
    Trigger intakeReverse = xboxController.pov(180);

    retract.whileTrue(new ClimberClimb(climber));
    extend.whileTrue(new ClimberExtend(climber));
    load.whileTrue(new IntakeLoad(intake));
    shoot.whileTrue(new ShooterShoot(shooter)).whileTrue(new IntakeUnload(intake));
    forward.whileTrue(new ArmForward(arm));
    reverse.whileTrue(new ArmReverse(arm));
    intakeReverse.whileTrue(new IntakeReverse(intake)).whileTrue(new ShooterReverse(shooter));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  // An example command will be run in autonomous
  // return Autos.exampleAuto(m_exampleSubsystem);
  // }
}
