package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RotateClimbHooksBackCommand extends CommandBase {
    
    RobotContainer m_container;
    
    public RotateClimbHooksBackCommand(RobotContainer container) {
        m_container = container;
    }

    @Override
    public void initialize() {
        m_container.climb.rotateClimbHooksBack();
    }
}
