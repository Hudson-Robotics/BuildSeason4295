package frc.robot.subsystems.DriveTrain;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.kauailabs.navx.frc.AHRS;

public class DriveTrain extends SubsystemBase {
    private final AHRS navX = new AHRS(SPI.Port.kMXP);

    private final Translation2d frontLeftLocation = new Translation2d(0.381, 0.381);
    private final Translation2d frontRightLocation = new Translation2d(0.381, -0.381);
    private final Translation2d backLeftLocation = new Translation2d(-0.381, 0.381);
    private final Translation2d backRightLocation = new Translation2d(-0.381, -0.381);

    private final NeoSwerve frontLeft = new NeoSwerve(1, 2, "Front Left");
    private final NeoSwerve frontRight = new NeoSwerve(3, 4, "Front Right");
    private final NeoSwerve backLeft = new NeoSwerve(5, 6, "Back Left");
    private final NeoSwerve backRight = new NeoSwerve(7, 8, "Back Right");

    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            frontLeftLocation, frontRightLocation,
            backLeftLocation, backRightLocation);

    private final SwerveDriveOdometry odometry = new SwerveDriveOdometry(
        kinematics,
         navX.getRotation2d(),
         );

    public DriveTrain() {
        super();
    }

private SwerveModulePosition[] getSwerveModulePositions() {
return new SwerveModulePosition[]{
    frontLeft.getPosition(),
    frontRight.getPosition(),
    backLeft.getPosition(),
    backRight.getSgetPositiontate()}
}

    public void drive(double xSpeed, double ySpeed, double rot) {
        SwerveModulePosition[] swerveModulePositions = getSwerveModulePositions();
        SwerveDriveKinematics.normalizeWheelSpeeds(swerveModulePositions, 3);
        SwerveDriveKinematics.calculateSwerveModuleStates(xSpeed, ySpeed, rot, swerveModulePositions, 3);
        frontLeft.setDesiredState(swerveModulePositions[0]);
        frontRight.setDesiredState(swerveModulePositions[1]);
        backLeft.setDesiredState(swerveModulePositions[2]);
        backRight.setDesiredState(swerveModulePositions[3]);
    }

    public void resetOdometry() {
        odometry.resetPosition(navX.getRotation2d(), new Pose2d());
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public Command exampleMethodCommand() {
        // Inline construction of command goes here.
        // Subsystem::RunOnce implicitly requires `this` subsystem.
        return runOnce(
                () -> {
                    /* one-time action goes here */
                });
    }

    public boolean exampleCondition() {
        // Query some boolean state, such as a digital sensor.
        return false;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

}
