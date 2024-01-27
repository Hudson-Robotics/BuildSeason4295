package frc.robot.subsystems.DriveTrain;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;

import edu.wpi.first.math.controller.PIDController;

import com.revrobotics.CANSparkLowLevel.MotorType;

public class NeoSwerve {
    private String Name;

    private CANSparkMax turningMotor;
    private RelativeEncoder turningEncoder;
    private RelativeEncoder altTurningEncoder;
    private PIDController turningPID;

    private CANSparkMax driveMotor;
    private RelativeEncoder driveEncoder;

    private static final SparkMaxAlternateEncoder.Type kAltEncType = SparkMaxAlternateEncoder.Type.kQuadrature;

    public NeoSwerve(int driveMotorCanbusAddress, int turningMotorCanbusAddress, String Name) {
        turningMotor = new CANSparkMax(turningMotorCanbusAddress, MotorType.kBrushless);
        turningEncoder = turningMotor.getEncoder();
        altTurningEncoder = turningMotor.getAlternateEncoder(kAltEncType, 4096);

        driveMotor = new CANSparkMax(turningMotorCanbusAddress, MotorType.kBrushless);

        this.Name = Name;
    }

}
