// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ColorSensor.ColorType;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PostShooterPrepFiringCommandGroup extends SequentialCommandGroup {
  /** Creates a new PostShooterPrepFiringCommandGroup. */
  public PostShooterPrepFiringCommandGroup(RobotContainer container) {
    addCommands(
      new FeedCargoAndRetractCommand(container.shooter, 0.25),
      new WaitUntilCommand(() -> container.colorSensor.readColor() != ColorType.None || container.oi.getFireOverrided()).withTimeout(2),
      new FeedCargoAndRetractCommand(container.shooter, 0.25),
      new WaitCommand(1.5),
      new InstantCommand(() -> {
          container.shooter.setVelocity(0);
          container.shooter.setAccelarator(0);
      }, container.shooter)
);
  }
}
