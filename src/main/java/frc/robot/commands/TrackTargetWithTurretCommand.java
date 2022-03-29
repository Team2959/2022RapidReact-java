package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class TrackTargetWithTurretCommand extends CommandBase {
    private final RobotContainer m_container;
    
    public TrackTargetWithTurretCommand(RobotContainer container) {
        m_container = container;
    }

    @Override
    public void execute() {
        double angle = m_container.turret.getAngleDegrees() + m_container.vision.getTY();
        if(m_container.turret.isCloseEnoughToTarget(angle)) {
            m_container.turret.setSpeedToTargetAngle(angle);
        } 
        else {
            m_container.turret.setSpeed(0);
        }
    }
}
