package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.DashboardMap;
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
            new InstantCommand(() -> {
              m_container.accelarator.setSpeed(SmartDashboard.getNumber(DashboardMap.kAcceleratorSpeed, 0.0));
              m_container.cargoIndexer.setSpeed(SmartDashboard.getNumber(DashboardMap.kCargoIndexerSpeed, 0.0));
            }),
            new WaitCommand(3.0)
            // KFR: potentially refactor to this
            // new TuneShooterAndHoodCommand(container),
            // new PostShooterPrepFiringCommandGroup(container)
        );
    }

    @Override
    public void end(boolean interupt) {
      m_container.shooter.setVelocity(Shooter.kIdleSpeed);
      m_container.accelarator.setSpeed(0);
      m_container.cargoIndexer.setSpeed(0);
    }
  }
