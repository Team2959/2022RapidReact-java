// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveOnlyAutoCommand extends SequentialCommandGroup {
  /** Creates a new DriveOnlyAutoCommand. */
  public DriveOnlyAutoCommand(RobotContainer container) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new WaitCommand(.25),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, -3, 0, false);
            }, container.drivetrain),
            new WaitCommand(3),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, 0, 0, false);
            }, container.drivetrain)
    );
  }
}