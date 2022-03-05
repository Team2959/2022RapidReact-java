package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.RobotContainer;

public class TuneShooterAndHoodCommand extends ParallelCommandGroup {
    public TuneShooterAndHoodCommand(RobotContainer container) {
        addCommands(
            new TurnTurretToPositionCommand(container),
            new SetHoodAngleCommand(container),
            new SetShooterSpeedCommand(container)
        );
    }
}
