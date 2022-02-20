package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class FireCommand extends SequentialCommandGroup {
    public FireCommand(RobotContainer container) {
        addCommands(
            new TuneShooterAndHoodCommand(container),
            new FeedBallCommand(container),
            new WaitCommand(1),
            new RetractFeederCommand(container)
        );
    }
}
