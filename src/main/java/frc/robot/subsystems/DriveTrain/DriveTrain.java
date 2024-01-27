package frc.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase  {
    public DriveTrain() {
        super();
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
