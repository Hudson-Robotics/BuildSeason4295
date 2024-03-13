package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class Rumble extends Command {
  private XboxController m_controller;
  private double m_level;
  private int m_time;
  private int loopCtr;

  public Rumble(double level, double time) {
    m_controller = RobotContainer.xboxController.getHID();
    m_level = level;
    m_time = (int) (time * 50);// 20ms in 1 sec
  }

  @Override
  public void initialize() {
    loopCtr = 0;
  }

  @Override
  public void execute() {
    loopCtr++;
    m_controller.setRumble(RumbleType.kBothRumble, m_level);
  }

  @Override
  public void end(boolean interrupted) {
    m_controller.setRumble(RumbleType.kBothRumble, 0);
  }

  @Override
  public boolean isFinished() {
    return loopCtr > m_time;
  }
}
