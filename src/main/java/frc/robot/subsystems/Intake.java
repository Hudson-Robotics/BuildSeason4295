// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Ports;
import frc.robot.commands.IntakeStop;

public class Intake extends SubsystemBase {
  private final TalonFX intakeMotor = new TalonFX(Ports.kIntake);
  public Intake() {
    setDefaultCommand(new IntakeStop(this));
  }

  public void Stop() {
    intakeMotor.set(0);
  }
  public void Load() {
    intakeMotor.set(0.4);
  }
  public void Unload(){
    intakeMotor.set(0.4);
  }
  public void Reverse(){
    intakeMotor.set(-.3);
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
