// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.DriveTrain;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Ports;

public class Tank extends SubsystemBase {

  CANSparkMax FrontLeft, FrontRight, BackLeft, BackRight;

  private DifferentialDrive tankDrive;

  public void Tank() {
    CANSparkMax FrontLeft = new CANSparkMax(Ports.kFrontLeftDrive, MotorType.kBrushless);
    CANSparkMax FrontRight = new CANSparkMax(Ports.kFrontRightDrive, MotorType.kBrushless);
    CANSparkMax BackLeft = new CANSparkMax(Ports.kBackLeftDrive, MotorType.kBrushless);
    CANSparkMax BackRight = new CANSparkMax(Ports.kBackLeftDrive, MotorType.kBrushless);

    BackLeft.follow(FrontLeft);
    BackRight.follow(FrontRight);

    tankDrive = new DifferentialDrive(FrontLeft, FrontRight);
  }

  public void drive (double leftStick, double rightStick){
    tankDrive.tankDrive(leftStick, rightStick);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
