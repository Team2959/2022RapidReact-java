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
                container.drivetrain.drive(-1.35, 0, 0, false);
            }, container.drivetrain),
            new IntakeToggleCommand(container),
            new WaitCommand(1.25),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, 0, 0, false);
            }, container.drivetrain),
            new WaitCommand(1),
            new InstantCommand(() -> {
                container.drivetrain.drive(1.35, 0, 0, false);
            }, container.drivetrain),
            new WaitCommand(.75),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, 0, 0, false);
            }, container.drivetrain),
            new FireCommand(container),
            new SetHoodAngleCommand(container, 1)
        );
    }
}
