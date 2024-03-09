package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IOConstants;
import frc.robot.subsystems.DriveTrain.DriveTrain;

public class JoystickCmd extends Command {
    private final DriveTrain driveTrain;
    private final Supplier<Double> xSpeedFunc, ySpeedFunc, turningSpeedFunc;
    private final Supplier<Boolean> fieldOrientationFunc;
    private final SlewRateLimiter xLimiter, yLimiter, turnLimiter;

    public JoystickCmd(DriveTrain subsystem, Supplier<Double> xSpeedFunc, Supplier<Double> ySpeed,
            Supplier<Double> turningSpeed, Supplier<Boolean> fieldOrientation) {
        driveTrain = subsystem;
        this.xSpeedFunc = xSpeedFunc;
        this.ySpeedFunc = ySpeed;
        this.turningSpeedFunc = turningSpeed;
        this.fieldOrientationFunc = fieldOrientation;
        xLimiter = new SlewRateLimiter(IOConstants.kTeleMaxAccel);
        yLimiter = new SlewRateLimiter(IOConstants.kTeleMaxAccel);
        turnLimiter = new SlewRateLimiter(IOConstants.kTeleMaxAngularAccel);
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double xSpeed = xSpeedFunc.get();
        double ySpeed = ySpeedFunc.get();
        double turningSpeed = turningSpeedFunc.get();
        boolean fieldOrientation = fieldOrientationFunc.get();

        // Deadband
        xSpeed = Math.abs(xSpeed) < IOConstants.kDeadband ? 0 : xSpeed;
        ySpeed = Math.abs(ySpeed) < IOConstants.kDeadband ? 0 : ySpeed;
        turningSpeed = Math.abs(turningSpeed) < 0.1 ? 0 : turningSpeed;

        // Slew rate limit
        xSpeed = xLimiter.calculate(xSpeed);
        ySpeed = yLimiter.calculate(ySpeed);
        turningSpeed = turnLimiter.calculate(turningSpeed);

        ChassisSpeeds speeds;
        if (fieldOrientation) {
            speeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, turningSpeed, driveTrain.getHeading2d());
        } else {
            speeds = new ChassisSpeeds(xSpeed, ySpeed, turningSpeed);
        }

        SwerveModuleState[] states = driveTrain.kinematics.toSwerveModuleStates(speeds);

        driveTrain.setModuleDesriedStates(states);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.stopModules();
    }

    @Override
    public boolean isFinished() {
        return false;  // Runs until interrupted
    }
}
