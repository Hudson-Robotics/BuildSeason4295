package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArmForward;
import frc.robot.commands.ArmReverse;
import frc.robot.commands.ClimberClimb;
import frc.robot.commands.ClimberExtend;
import frc.robot.commands.IntakeLoad;
import frc.robot.commands.ShooterShoot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
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

  private final CommandXboxController xboxController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);

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

    retract.whileTrue(new ClimberClimb(climber));
    extend.whileTrue(new ClimberExtend(climber));
    load.whileTrue(new IntakeLoad(intake));
    shoot.whileTrue(new ShooterShoot(shooter));
    forward.whileTrue(new ArmForward(arm));
    reverse.whileTrue(new ArmReverse(arm));

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
