// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ClimberStop;
import frc.robot.Constants.Ports;

public class Climber extends SubsystemBase {

  private final CANSparkMax climberLeft = new CANSparkMax(Ports.kClimberLeft, CANSparkMax.MotorType.kBrushless);
  private final CANSparkMax climberRight = new CANSparkMax(Ports.kClimberRight, CANSparkMax.MotorType.kBrushless);
  public Climber() {
    climberRight.setInverted(true);
    setDefaultCommand(new ClimberStop(this));
  }

  public void Stop() {
    climberLeft.set(0);
    climberRight.set(0);
  }
  public void Climb() {
    climberLeft.set(0.1);
    climberRight.set(0.1);
  }

  public void Extend() {
    climberLeft.set(-0.1);
    climberRight.set(-0.1);
  }
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

public Command retract() {   return runOnce(
        () -> {
          /* one-time action goes here */
        });}


  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
