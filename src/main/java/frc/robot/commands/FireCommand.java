package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ColorSensor.ColorType;

public class FireCommand extends SequentialCommandGroup {

    private final RobotContainer m_container;

    public FireCommand(RobotContainer container) {
        m_container = container;

        addCommands(
            new TuneShooterAndHoodCommand(container),
            new WaitCommand(1),
            new FeedCargoAndRetractCommand(container.cargoFeeder, 0.25),
            new SnapTurretToTarget(container),
            new WaitCommand(.25),
            new WaitUntilCommand(() -> container.colorSensor.readColor() != ColorType.None || container.oi.getFireOverrided()).withTimeout(1),
            new WaitCommand(0.75),
            new FeedCargoAndRetractCommand(container.cargoFeeder, 0.25),
            new WaitCommand(1.0)

            // KFR: potentially refactor to this
            // new TuneShooterAndHoodCommand(container),
            // new PostShooterPrepFiringCommandGroup(container)
        );
    }

    @Override
    public void end(boolean interupt) {
      m_container.shooter.setVelocity(Shooter.kIdleSpeed);
      m_container.shooter.setAccelarator(0);
    }
  }
