package frc.robot.subsystems.DriveTrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.kauailabs.navx.frc.AHRS;

public class DriveTrain extends SubsystemBase {
    private final AHRS navX = new AHRS(SPI.Port.kMXP);

    private final Translation2d frontLeftLocation = new Translation2d(0.381, -0.381);
    private final Translation2d frontRightLocation = new Translation2d(0.381, 0.381);
    private final Translation2d backLeftLocation = new Translation2d(-0.381, 0.381);
    private final Translation2d backRightLocation = new Translation2d(-0.381, -0.381);

    private final NeoSwerve frontLeft = new NeoSwerve(1, 2, "Front Left");
    private final NeoSwerve frontRight = new NeoSwerve(3, 4, "Front Right");
    private final NeoSwerve backLeft = new NeoSwerve(5, 6, "Back Left");
    private final NeoSwerve backRight = new NeoSwerve(7, 8, "Back Right");

    public final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);

    private final SwerveDriveOdometry odometry = new SwerveDriveOdometry(
            kinematics,
            new Rotation2d(0),
            getSwerveModulePositions());

    public DriveTrain() {
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Wait for the NavX to self calibrate
                navX.reset(); // Reset the NavX
            } catch (Exception e) {
            }
        }).start();
    }

    public double getHeading() {
        return Math.IEEEremainder(navX.getAngle(), 360);
    }

    public Rotation2d getHeading2d() {
        return Rotation2d.fromDegrees(getHeading());
    }

    private SwerveModulePosition[] getSwerveModulePositions() {
        return new SwerveModulePosition[] {
                frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition() };
    }

    // public void drive(double xSpeed, double ySpeed, double rot, boolean
    // fieldRelative) {
    // var swerveModuleStates = kinematics.toSwerveModuleStates(
    // fieldRelative
    // ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot,
    // navX.getRotation2d())
    // : new ChassisSpeeds(xSpeed, ySpeed, rot));

    // setModuleDesriedStates(swerveModuleStates);

    // SmartDashboard.putNumber("xSpeed", xSpeed);
    // SmartDashboard.putNumber("ySpeed", ySpeed);
    // SmartDashboard.putNumber("rot", rot);
    // SmartDashboard.putBoolean("fieldRelative", fieldRelative);

    // SmartDashboard.putNumber("Heading", getHeading());
    // }

    public void setModuleDesriedStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, 9);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);
    }

    public void resetOdometry() {
        odometry.resetPosition(navX.getRotation2d(), getSwerveModulePositions(), new Pose2d());
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

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

    public void ResetDistance() {
        frontLeft.ResetDistance();
        frontRight.ResetDistance();
        backLeft.ResetDistance();
        backRight.ResetDistance();
    }

    public double GetDistance() {
        return (frontLeft.getDistance() + frontRight.getDistance() + backLeft.getDistance() + backRight.getDistance())
                / 4;
    }

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
        frontLeft.updateOdometry();
        frontRight.updateOdometry();
        backLeft.updateOdometry();
        backRight.updateOdometry();
        SmartDashboard.putString("NavX", navX.getRotation2d().toString());
        SmartDashboard.putString("FrontLeftAngle", frontLeft.getState().toString());
        SmartDashboard.putString("FrontRightAngle", frontRight.getState().toString());
        SmartDashboard.putString("BackLeftAngle", backLeft.getState().toString());
        SmartDashboard.putString("BackRightAngle", backRight.getState().toString());
        SmartDashboard.putString("Robot Location", getPose().getTranslation().toString());
    }

    public void stopModules() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }

}
