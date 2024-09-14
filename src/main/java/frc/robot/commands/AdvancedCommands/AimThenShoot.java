package frc.robot.commands.AdvancedCommands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import frc.robot.commands.Arm.Speaker;
import frc.robot.commands.Intake.PassIn;
import frc.robot.commands.Shooter.Shoot;

public class AimThenShoot extends SequentialCommandGroup
{
    public AimThenShoot(Arm arm, Intake intake, Shooter shoot)
    {
        addCommands(
            new Speaker(arm),
            new ParallelCommandGroup(new Shoot(shoot), new PassIn(intake))
        );
    }
}
