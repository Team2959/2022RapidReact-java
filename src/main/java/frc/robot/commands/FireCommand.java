package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ColorSensor.ColorType;

public class FireCommand extends SequentialCommandGroup {
    public FireCommand(RobotContainer container) {
        addCommands(
            // new TurnTurretToPositionCommand(container)
            // new WaitUntilCommand(() -> container.colorSensor.readColor() != ColorType.None),
            new TuneShooterAndHoodCommand(container),
            new WaitCommand(1),
            new FeedCargoAndRetractCommand(container.shooter, 0.25),
            new SnapTurretToTarget(container),
            new WaitCommand(.25),
            new WaitUntilCommand(() -> container.colorSensor.readColor() != ColorType.None || container.oi.getFireOverrided()).withTimeout(1),
            new WaitCommand(0.75),
            new FeedCargoAndRetractCommand(container.shooter, 0.25),
            new WaitCommand(1.0),
            new InstantCommand(() -> {
                container.shooter.setVelocity(Shooter.kIdleSpeed);
                container.shooter.setAccelarator(0);
            }, container.shooter)

            // KFR: potentially refactor to this
            // new TuneShooterAndHoodCommand(container),
            // new PostShooterPrepFiringCommandGroup(container)
        );
    }
}
