package frc.robot.commands;

import cwtech.util.Util;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SetShooterSpeedCommand extends CommandBase {
    private final RobotContainer m_container;
    private final double m_speed;

    public SetShooterSpeedCommand(RobotContainer container, double speed) {
        m_container = container;
        m_speed = speed;

        addRequirements(m_container.shooter);
    }

    @Override
    public void initialize() {
        m_container.shooter.setVelocity(m_speed);
        m_container.shooter.setAccelarator(1.0);
    }

    @Override
    public boolean isFinished() {
        return Util.dcompareMine(m_container.shooter.getVelocity(), m_speed, 0.001);
    }
}
