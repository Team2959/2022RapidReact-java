package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class FireCommand extends SequentialCommandGroup {
    public FireCommand(RobotContainer container) {
        addCommands(
            // new TurnTurretToPositionCommand(container)
            new TuneShooterAndHoodCommand(container),
            // new WaitCommand(1.5),
            new FeedCargoAndRetractCommand(container.shooter, 0.25),
            new WaitCommand(1.2),
            new FeedCargoAndRetractCommand(container.shooter, 0.25),
            new WaitCommand(1.5),
            //new SetShooterSpeedCommand(container, 0)
            new RunCommand(() -> {
                container.shooter.setVelocity(0);
                container.shooter.setAccelarator(0);
            }, container.shooter)
        );
    }
}
