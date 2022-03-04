package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import cwtech.util.Util;
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
        
        //m_container.shooter.setVelocity(m_speed);
        //m_container.shooter.setAccelaratorVelocity(m_speed * 1.3);
        
        m_container.shooter.setVelocity(m_speed);
        m_container.shooter.setAccelarator(m_speed > 0 ? SmartDashboard.getNumber("Accelarator Speed", 0.85) : 0.0);
    }

    @Override
    public boolean isFinished() {
        //return Util.dcompareMine(container.shooter.getVelocity(), m_speed, m_speed * 0.05);
        return true;
    }
}
