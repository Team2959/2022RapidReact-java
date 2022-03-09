// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LowGoalWallShotCommandGroup extends SequentialCommandGroup {
  /** Creates a new LowGoalWallShotCommandGroup. */
  public LowGoalWallShotCommandGroup(RobotContainer container) {
    addCommands(
      new SetFixedTurretHoodAndShooterCommandGroup(container, 0.0, 40, 600.0),
      new PostShooterPrepFiringCommandGroup(container)
    );
  }
}
