// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class Ports {
    public static final int kDriverControllerPort = 0;

    public static final int kFrontLeftTurning = 1;
    public static final int kFrontLeftDrive = 2;

    public static final int kFrontRightTurning = 3;
    public static final int kFrontRightDrive = 4;

    public static final int kBackLeftTurning = 5;
    public static final int kBackLeftDrive = 6;

    public static final int kBackRightTurning = 7;
    public static final int kBackRightDrive = 8;

    public static final int kClimberLeft = 9;
    public static final int kClimberRight = 10;

    public static final int kArmLeft = 11;
    public static final int kArmRight = 12;

    public static final int kIntake = 13;
    public static final int kShooter = 14;
  }

  public static final double WHEEL_RADIUS_IN_METERS = 0.0508;

}
