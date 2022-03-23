package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class AutoComeForwardCommand extends SequentialCommandGroup {
    public AutoComeForwardCommand(RobotContainer container) {
        addCommands(
            new WaitCommand(1),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, -3, 0, false);
            }, container.drivetrain),
            new IntakeToggleCommand(container),
            new WaitCommand(3),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, 0, 0, false);
            }, container.drivetrain),
            new WaitCommand(1),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, 3, 0, false);
            }, container.drivetrain),
            new WaitCommand(3),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, 0, 0, false);
            }, container.drivetrain),
            new FireCommand(container),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, -3, 0, false);
            }, container.drivetrain),
            new WaitCommand(3),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, 0, 0, false);
            }, container.drivetrain),
            new SetHoodAngleCommand(container, 1)
        );
    }
}
