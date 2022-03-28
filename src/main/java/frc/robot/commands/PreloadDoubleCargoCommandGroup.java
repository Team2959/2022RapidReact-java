package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ColorSensor.ColorType;

public class PreloadDoubleCargoCommandGroup extends SequentialCommandGroup {
    public PreloadDoubleCargoCommandGroup(RobotContainer container) {
        addCommands(
            new InstantCommand(() -> {
                container.shooter.setAccelarator(0.3);
            }, container.shooter),
            new FeedCargoAndRetractCommand(container.shooter, .25),
            new WaitUntilCommand(() -> container.colorSensor.readColor() != ColorType.None || container.oi.getFireOverrided()).withTimeout(1),
            new WaitCommand(0.5),
            new InstantCommand(() -> {
                container.shooter.setAccelarator(0.0);
            }, container.shooter)
        );
    }
}
