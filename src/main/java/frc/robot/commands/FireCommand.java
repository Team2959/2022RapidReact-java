package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class FireCommand extends SequentialCommandGroup {
    public FireCommand(RobotContainer container) {
        addCommands(
            // new SnapTurretToTarget(container),
            new TuneShooterAndHoodCommand(container),
            new WaitCommand(1.5),
            // new FeedCargoAndRetractCommand(container.shooter, 0.25),
            new FeedBallCommand(container),
            new WaitCommand(0.25),
            new RetractFeederCommand(container),
            new WaitCommand(0.5),
            // new FeedCargoAndRetractCommand(container.shooter, 0.25),
            new FeedBallCommand(container),
            new WaitCommand(0.25),
            new RetractFeederCommand(container),
            new WaitCommand(1.5),
            new SetShooterSpeedCommand(container, 0)
        );
    }
}
