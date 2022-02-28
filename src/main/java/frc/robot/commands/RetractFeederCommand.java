package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class RetractFeederCommand extends InstantCommand {
    private final RobotContainer m_container;

    public RetractFeederCommand(RobotContainer container) {
        m_container = container;
    }
    
    @Override
    public void initialize() {
        m_container.shooter.setFeeder(false);
    }
}
