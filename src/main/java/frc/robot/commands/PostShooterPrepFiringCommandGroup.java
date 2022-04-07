// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ColorSensor.ColorType;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PostShooterPrepFiringCommandGroup extends SequentialCommandGroup {

    private final RobotContainer m_container;

    public PostShooterPrepFiringCommandGroup(RobotContainer container) {
        m_container = container;

        addCommands(
                new FeedCargoAndRetractCommand(container.cargoFeeder, 0.25),
                new WaitCommand(.25),
                new WaitUntilCommand(
                        () -> container.colorSensor.readColor() != ColorType.None || container.oi.getFireOverrided())
                                .withTimeout(1),
                new WaitCommand(0.75),
                new FeedCargoAndRetractCommand(container.cargoFeeder, 0.25),
                new WaitCommand(1.0));
    }

    @Override
    public void end(boolean interupt) {
        m_container.shooter.setFrontVelocity(Shooter.kIdleSpeed);
        m_container.shooter.setBackVelocity(Shooter.kIdleSpeed);
        m_container.shooter.setAccelarator(0);
        m_container.turret.setDesiredAngle(0.0);
    }
}
