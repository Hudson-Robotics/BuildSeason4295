package frc.robot;

import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  
  private DoubleArraySubscriber das; 
  @Override
  public void robotInit() {
  //  CameraServer.startAutomaticCapture();
    m_robotContainer = new RobotContainer();
    NetworkTable table = NetworkTableInstance.getDefault().getTable("TEST");
    das = table.getDoubleArrayTopic("TEST").subscribe(new double[] {});
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
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
    double[] areas = das.get();
    for(double area : areas)
    {
      System.out.println(area);
    }
    System.out.println();
  }

}
