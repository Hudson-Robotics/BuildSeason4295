package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringTopic;
//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Vision;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private StringTopic strTopic;
  private Vision vision;

  @Override
  public void robotInit() {
    //  CameraServer.startAutomaticCapture();
    NetworkTableInstance test = NetworkTableInstance.create();
    strTopic = new StringTopic(test, 0);
    m_robotContainer = new RobotContainer();
    vision = new Vision(strTopic);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    vision.periodic();
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

}
