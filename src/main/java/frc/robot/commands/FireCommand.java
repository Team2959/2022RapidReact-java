package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ColorSensor.ColorType;

public class FireCommand extends SequentialCommandGroup {
    public FireCommand(RobotContainer container) {
        addCommands(
            // new TurnTurretToPositionCommand(container)
            // new WaitUntilCommand(() -> container.colorSensor.readColor() != ColorType.None),
            new TuneShooterAndHoodCommand(container),
            new WaitCommand(0),
            new FeedCargoAndRetractCommand(container.shooter, 0.25),
            new SetShooterSpeedCommand(container),
            new SnapTurretToTarget(container),
            new WaitUntilCommand(() -> container.colorSensor.readColor() != ColorType.None || container.oi.getFireOverrided()),
            new WaitCommand(.5),
            new FeedCargoAndRetractCommand(container.shooter, 0.25),
            new WaitCommand(1.5),
            //new SetShooterSpeedCommand(container, 0)
            new RunCommand(() -> {
                container.shooter.setVelocity(0);
                container.shooter.setAccelarator(0);
            }, container.shooter)

            // KFR: potentially refactor to this
            // new TuneShooterAndHoodCommand(container),
            // new PostShooterPrepFiringCommandGroup(container)
        );
    }
}
