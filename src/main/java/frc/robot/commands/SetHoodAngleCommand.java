package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SetHoodAngleCommand extends CommandBase {
    private final RobotContainer m_container;
    private final double m_hoodPosition;
    private boolean m_backwards;
    public SetHoodAngleCommand(RobotContainer container, double hoodPosition) { 
        m_container = container;
        m_hoodPosition = hoodPosition;
        m_backwards = false;

        addRequirements(m_container.hood);
    }

    @Override
    public void initialize() {
        double hoodPosition = m_container.hood.getPosition();

        if(hoodPosition < m_hoodPosition) {
            m_container.hood.setSpeed(.75);
            m_backwards = false;
        } 
        else {
            m_container.hood.setSpeed(-0.75);
            m_backwards = true;
        }
    }

    @Override
    public boolean isFinished() {
        double currentHoodPosition = m_container.hood.getPosition();
        // Checks to see if at or pass requested hood position and if so stop
        return m_backwards ? currentHoodPosition <= m_hoodPosition : currentHoodPosition >= m_hoodPosition;
    }

    @Override
    public void end(boolean interrupted) {
        m_container.hood.setSpeed(0.0);
    }
    
}
