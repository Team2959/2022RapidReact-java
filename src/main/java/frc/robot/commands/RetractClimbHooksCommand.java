package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RetractClimbHooksCommand extends CommandBase {
    private final RobotContainer m_container;
    private final boolean m_safe;

    public RetractClimbHooksCommand(RobotContainer container, boolean safe) {
        m_container = container;
        m_safe = safe;
        
        addRequirements(m_container.climb, m_container.turret);
    }

    @Override
    public void initialize() {
        m_container.turret.setDesiredAngle(0.0);
        m_container.climb.retractClimbHooks(m_safe);
    }

    @Override
    public void end(boolean interupt) {
        m_container.climb.keepCurrentPosition();
    }
}
