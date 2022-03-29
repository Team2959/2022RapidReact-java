package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.RobotContainer;

public class FireCommandGroup extends ParallelRaceGroup {
    public FireCommandGroup(RobotContainer container) {
        addCommands(
            new FireCommand(container),
            new TrackTargetWithTurretCommand(container)
        );
    }
}
