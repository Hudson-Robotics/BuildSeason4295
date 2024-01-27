package frc.robot.subsystems.DriveTrain;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import com.revrobotics.CANSparkLowLevel.MotorType;

public class NeoSwerve {
    private String Name;

    private CANSparkMax turningMotor;
    private RelativeEncoder turningEncoder;
    private RelativeEncoder altTurningEncoder;
    private PIDController turningPID;
    private double kP = 0.5;
    private double kI = 0;
    private double kD = 0;

    private CANSparkMax driveMotor;
    private RelativeEncoder driveEncoder;

    private static final SparkMaxAlternateEncoder.Type kAltEncType = SparkMaxAlternateEncoder.Type.kQuadrature;

    public NeoSwerve(int driveMotorCanbusAddress, int turningMotorCanbusAddress, String Name) {
        turningMotor = new CANSparkMax(turningMotorCanbusAddress, MotorType.kBrushless);
        turningEncoder = turningMotor.getEncoder();
        altTurningEncoder = turningMotor.getAlternateEncoder(kAltEncType, 4096);

        driveMotor = new CANSparkMax(turningMotorCanbusAddress, MotorType.kBrushless);
        driveEncoder = driveMotor.getEncoder();

        this.Name = Name;

        turningPID = new PIDController(kP, kI, kD);
    }

    private Rotation2d getAngle() {
        return new Rotation2d(altTurningEncoder.getPosition() * Math.PI);
    }

    private double getSpeed() {
        return driveEncoder.getVelocity() / 2 * Math.PI * 0.0508 / 60;
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getSpeed(), getAngle());
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        SwerveModuleState optomized = SwerveModuleState.optimize(desiredState, getAngle());

        driveMotor.set(optomized.speedMetersPerSecond / 3);

        double turningSpeed = turningPID.calculate(getAngle().getRadians(), optomized.angle.getRadians());

        turningMotor.set(turningSpeed);
    }
}
