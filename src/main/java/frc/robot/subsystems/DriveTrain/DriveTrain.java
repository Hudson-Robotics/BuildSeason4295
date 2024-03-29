package frc.robot.subsystems.DriveTrain;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Ports;
import frc.robot.Constants.SlewRates;

import com.kauailabs.navx.frc.AHRS;

public class DriveTrain extends SubsystemBase {
    private final AHRS navX = new AHRS(SPI.Port.kMXP);

    private final Translation2d frontLeftLocation = new Translation2d(0.381, -0.381);
    private final Translation2d frontRightLocation = new Translation2d(0.381, 0.381);
    private final Translation2d backLeftLocation = new Translation2d(-0.381, 0.381);
    private final Translation2d backRightLocation = new Translation2d(-0.381, -0.381);

    private final NeoSwerve frontLeft = new NeoSwerve(Ports.kFrontLeftTurning, Ports.kFrontLeftDrive, "Front Left");
    private final NeoSwerve frontRight = new NeoSwerve(Ports.kFrontRightTurning, Ports.kFrontRightDrive, "Front Right");
    private final NeoSwerve backLeft = new NeoSwerve(Ports.kBackLeftTurning, Ports.kBackLeftDrive, "Back Left");
    private final NeoSwerve backRight = new NeoSwerve(Ports.kBackRightTurning, Ports.kBackRightDrive, "Back Right");

    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);

    private final SwerveDriveOdometry odometry = new SwerveDriveOdometry(
            kinematics,
            navX.getRotation2d(),
            getSwerveModulePositions());

    private final SlewRateLimiter slrX = new SlewRateLimiter(SlewRates.kDriveX);
    private final SlewRateLimiter slrY = new SlewRateLimiter(SlewRates.kDriveY);
    private final SlewRateLimiter slrRot = new SlewRateLimiter(SlewRates.kDriveRot);

    public DriveTrain() {
        ResetNavX();
    }

    public void ResetNavX() {
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Wait for the NavX to self calibrate
                navX.reset(); // Reset the NavX
            } catch (Exception e) {
            }
        }).start();
    }

    private SwerveModulePosition[] getSwerveModulePositions() {
        return new SwerveModulePosition[] {
                frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition() };
    }

    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {

        xSpeed = slrX.calculate(xSpeed);
        ySpeed = slrY.calculate(ySpeed);
        rot = slrRot.calculate(rot);

        var swerveModuleStates = kinematics.toSwerveModuleStates(
                fieldRelative
                        ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, navX.getRotation2d().unaryMinus())
                        : new ChassisSpeeds(xSpeed, ySpeed, rot));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, 9);

        frontLeft.setDesiredState(swerveModuleStates[0]);
        frontRight.setDesiredState(swerveModuleStates[1]);
        backLeft.setDesiredState(swerveModuleStates[2]);
        backRight.setDesiredState(swerveModuleStates[3]);

        SmartDashboard.putNumber("xSpeed", xSpeed);
        SmartDashboard.putNumber("ySpeed", ySpeed);
        SmartDashboard.putNumber("rot", rot);
        SmartDashboard.putBoolean("fieldRelative", fieldRelative);
    }

    public void resetOdometry() {
        odometry.resetPosition(navX.getRotation2d(), getSwerveModulePositions(), new Pose2d());
    }

    // public Pose2d getPose() {
    // return odometry.getPoseMeters();
    // }

    // public Command Home() {
    // // Inline construction of command goes here.
    // // Subsystem::RunOnce implicitly requires `this` subsystem.
    // return runOnce(
    // () -> {
    // frontLeft.Reset();
    // frontRight.Reset();
    // backLeft.Reset();
    // backRight.Reset();
    // });
    // }

    public void Home() {
        frontLeft.Reset();
        frontRight.Reset();
        backLeft.Reset();
        backRight.Reset();
    }

    public boolean exampleCondition() {
        // Query some boolean state, such as a digital sensor.
        return false;
    }

    @Override
    public void periodic() {
        updateOdometry();
    }

    public void updateOdometry() {
        odometry.update(navX.getRotation2d(), getSwerveModulePositions());

        SmartDashboard.putString("NavX", navX.getRotation2d().unaryMinus().toString());
        SmartDashboard.putString("FrontLeftAngle", frontLeft.getState().toString());
        SmartDashboard.putString("FrontRightAngle", frontRight.getState().toString());
        SmartDashboard.putString("BackLeftAngle", backLeft.getState().toString());
        SmartDashboard.putString("BackRightAngle", backRight.getState().toString());

        frontLeft.updateOdometry();
        frontRight.updateOdometry();
        backLeft.updateOdometry();
        backRight.updateOdometry();
    }
}
