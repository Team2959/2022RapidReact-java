// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FireCommandWithTracking extends SequentialCommandGroup {

  private final RobotContainer m_container;

  public FireCommandWithTracking(RobotContainer container) {
    m_container = container;

    addCommands(
      new TuneShooterAndHoodCommand(container).withInterrupt(() -> container.oi.getFireOverrided()),
      new WaitCommand(1.0).withInterrupt(() -> container.oi.getFireOverrided()).raceWith(new ActiveTurretTracking(container)),
      new ActiveTargetTrackingAndFire(container),
      new WaitCommand(1)
    );
  }

  @Override
  public void end(boolean interupt) {
    m_container.m_activeTracking = false;
    m_container.shooter.setVelocity(Shooter.kIdleSpeed);
    m_container.shooter.setAccelarator(0);
    m_container.turret.setDesiredAngle(0.0);
  }
}
