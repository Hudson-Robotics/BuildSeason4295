
package frc.robot;

public final class Constants {

  public static class Speeds {
    public static final double kArmForward = 0.1;
    public static final double kArmReverse = 0.075;

    public static final double kClimberClimb = 0.5;
    public static final double kClimberExtend = 0.5;

    public static final double kIntakeLoad = 0.4;
    public static final double kIntakeUnload = 0.4;
    public static final double kIntakeReverse = 0.25;

    public static final double kShooterShoot = 0.5;
    public static final double kShooterReverse = 0.4;

  }

  public static class Positions {
    public static final double kArmForward = 10;
    public static final double kArmReverse = 28;

  }

  public static class SlewRates {
    public static final double kDriveX = 1.5;
    public static final double kDriveY = 1.5;
    public static final double kDriveRot = 1.5;
  }

  public static class Ports {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControlPort = 1;

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

    public static final int kForwardSwitch = 0;
    public static final int kBackSwitch = 1;

  }

  public static final double WHEEL_RADIUS_IN_METERS = 0.0508;

}
