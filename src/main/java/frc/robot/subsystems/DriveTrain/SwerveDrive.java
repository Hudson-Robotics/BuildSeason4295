package frc.robot.subsystems.DriveTrain;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.SlewRates;
import frc.robot.Interfaces.DriveTrain;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrive extends SubsystemBase implements DriveTrain 
{
    private final AHRS navX;

    private final SwerveModule frontLeft;
    private final SwerveModule frontRight;
    private final SwerveModule backLeft;
    private final SwerveModule backRight;

    // This could be a property for SwerveModule
    private final Translation2d frontLeftLocation;
    private final Translation2d frontRightLocation;
    private final Translation2d backLeftLocation;
    private final Translation2d backRightLocation;

    private final SwerveDriveKinematics kinematics;
    private final SwerveModulePosition[] positionsOfWheels;
    private final SwerveDriveOdometry odometry;

    private final SlewRateLimiter slewRateX;
    private final SlewRateLimiter slewRateY;
    private final SlewRateLimiter slewRateRotation;

    public SwerveDrive(SwerveModule frontLeft, SwerveModule frontRight, SwerveModule backLeft, SwerveModule backRight)
    {
        this.navX = new AHRS(SPI.Port.kMXP);
        
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;

        this.frontLeftLocation = new Translation2d(0.381, -0.381);
        this.frontRightLocation = new Translation2d(0.381, 0.381);
        this.backLeftLocation = new Translation2d(-0.381, 0.381);
        this.backRightLocation = new Translation2d(-0.381, -0.381);

        this.kinematics = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);

        this.positionsOfWheels = new SwerveModulePosition[4];

        this.odometry = new SwerveDriveOdometry(kinematics, navX.getRotation2d(), getSwerveModulePositions());

        this.slewRateX = new SlewRateLimiter(SlewRates.kDriveX);
        this.slewRateY = new SlewRateLimiter(SlewRates.kDriveY);
        this.slewRateRotation = new SlewRateLimiter(SlewRates.kDriveRot);
    }

    @Override
    public void drive(double xSpeed, double ySpeed, double rotation)
    {
        //not gonna implement fieldRelative, it looks like it is only set to True
        xSpeed = slewRateX.calculate(xSpeed);
        ySpeed = slewRateY.calculate(ySpeed);
        rotation = slewRateRotation.calculate(rotation);

        ChassisSpeeds speedOfChassis = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, navX.getRotation2d());
        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(speedOfChassis);

        frontLeft.setDesiredState(swerveModuleStates[0]);
        frontRight.setDesiredState(swerveModuleStates[1]);
        backLeft.setDesiredState(swerveModuleStates[2]);
        backRight.setDesiredState(swerveModuleStates[3]);

        //This might be mostly for debugging, maybe not needed
        SmartDashboard.putNumber("xSpeed", xSpeed);
        SmartDashboard.putNumber("ySpeed", ySpeed);
        SmartDashboard.putNumber("rot", rotation);
        SmartDashboard.putBoolean("fieldRelative", true);
    }

    public SwerveModulePosition[] getSwerveModulePositions()
    {
        this.positionsOfWheels[0] = frontLeft.getPosition();
        this.positionsOfWheels[1] = frontRight.getPosition();
        this.positionsOfWheels[2] = backLeft.getPosition();
        this.positionsOfWheels[3] = backRight.getPosition();

        return positionsOfWheels;
    }

    public void ResetNavX()
    {
        // NavX magic...
        new Thread(() -> {
            try
            {
                Thread.sleep(1000); // Wait for the NavX to self calibrate
                navX.reset(); // Reset the NavX
            }
            catch (Exception e)
            {

            }
        }).start();
    }

    public void resetOdometry()
    {
        //The last input takes in the position of the robot on the field...
        odometry.resetPosition(navX.getRotation2d(), getSwerveModulePositions(), new Pose2d());
    }

    public void setInitialPosition()
    {
        frontLeft.SetEncodersToZero();
        frontRight.SetEncodersToZero();
        backLeft.SetEncodersToZero();
        backRight.SetEncodersToZero();
    }

    public void updateOdometry() {
        odometry.update(navX.getRotation2d(), getSwerveModulePositions());

        //hmmm... not sure about sending the data from here...
        //TODO: Maybe make subsystem interface that has a function write to SmartDashboard... and it gets called periodically, make it more clear
        SmartDashboard.putString("NavX", navX.getRotation2d().unaryMinus().toString());
        SmartDashboard.putString("FrontLeftAngle", frontLeft.toString());
        SmartDashboard.putString("FrontRightAngle", frontRight.toString());
        SmartDashboard.putString("BackLeftAngle", backLeft.toString());
        SmartDashboard.putString("BackRightAngle", backRight.toString());

        frontLeft.updateOdometry();
        frontRight.updateOdometry();
        backLeft.updateOdometry();
        backRight.updateOdometry();
    }

    @Override
    public void periodic()
    {
        updateOdometry();
    }

}
