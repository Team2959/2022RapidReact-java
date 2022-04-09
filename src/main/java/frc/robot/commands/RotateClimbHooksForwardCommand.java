package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RotateClimbHooksForwardCommand extends CommandBase {
    
    RobotContainer m_container;
    
    public RotateClimbHooksForwardCommand(RobotContainer container) {
        m_container = container;
    }

    @Override
    public void initialize() {
        m_container.climb.rotateClimbHooksForward();
    }
}
