package frc.robot.subsystems.DriveTrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Interfaces.PIDMotor;


// look like com.ctre.phoenix6.mechanisms.swerve.SwerveModule has a class for SwerveModule, might be good to implement it
public class SwerveModule
{
    final private PIDMotor rotationMotor;
    final private PIDMotor driveMotor;

    final private String name;

    final private double PROPORTIONAL_CONSTANT = 0.5;
    final private double INTEGRAL_CONSTANT = 0;
    final private double DERIVATIVE_CONSTANT = 0;

    final private PIDController turningPID;
    final private SwerveModulePosition position;

    public SwerveModule(PIDMotor rotationMotor, PIDMotor driveMotor, String name)
    {
        this.rotationMotor = rotationMotor;
        this.driveMotor = driveMotor;
        this.name = name;

        turningPID = new PIDController(PROPORTIONAL_CONSTANT, INTEGRAL_CONSTANT, DERIVATIVE_CONSTANT);
        turningPID.enableContinuousInput(-Math.PI, Math.PI);

        position = new SwerveModulePosition();
    }

    private Rotation2d getAngle()
    {
        double radians = rotationMotor.getEncoderPosition() * 2 * Math.PI;
        return Rotation2d.fromRadians(radians);
    }

    private double getSpeed()
    {
        double circumference = 2 * Math.PI * Constants.WHEEL_RADIUS_IN_METERS;
        double rotationPerSecond = driveMotor.getEncoderSpeed() / 60;
        double metersPerSecond = rotationPerSecond * circumference;
        return metersPerSecond;
    }

    public SwerveModulePosition getPosition()
    {
        position.angle = getAngle();
        position.distanceMeters = driveMotor.getEncoderPosition(); // TODO: this is wrong it only gets the ticks im ps
        return position;
    }

    public void setDesiredState(SwerveModuleState desiredState)
    {
        if (Math.abs(desiredState.speedMetersPerSecond) < 0.1)
        {
            stop();
            return;
        }

        SwerveModuleState optimized = SwerveModuleState.optimize(desiredState, getAngle());
        driveMotor.setSpeed(optimized.speedMetersPerSecond); //TODO: this feels wrong, setting the speedMetersPerSecond to a PIDMotors that only reads from -1 to 1, VERY SUS

        double turningSpeed = turningPID.calculate(getAngle().getRadians(), optimized.angle.getRadians());
        turningSpeed = MathUtil.clamp(turningSpeed, -.25, 25);
        rotationMotor.setSpeed(turningSpeed);

        //Kinda Jank putting this here... oh whale
        SmartDashboard.putNumber(this.name + " Optomized Wheel Speed", optimized.speedMetersPerSecond);
        SmartDashboard.putNumber(this.name + " Optomized Wheel Angle", optimized.angle.getRadians());
    }

    //This also feels jank... too much work rn
    public void updateOdometry() {
        SmartDashboard.putNumber(this.name + " Output", rotationMotor.getSpeed());
        SmartDashboard.putNumber(this.name + " Postion Error", turningPID.getPositionError());

        SmartDashboard.putNumber(this.name + " Alternate Encoder", rotationMotor.getEncoderPosition() * Math.PI * 2);
    }

    public void stop()
    {
        rotationMotor.setSpeed(0);
        driveMotor.setSpeed(0);
    }

    public void SetEncodersToZero()
    {
        rotationMotor.setEncoderPosition(0);
        driveMotor.setEncoderPosition(0);
    }

    @Override
    public String toString()
    {
        return String.format("(Speed: %.2f m/s, Angle: %s)", getSpeed(), getAngle());
    }


}
