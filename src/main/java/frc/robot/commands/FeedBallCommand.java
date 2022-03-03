package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class FeedBallCommand extends InstantCommand {
    
    private final RobotContainer m_container;

    public FeedBallCommand(RobotContainer container) {
        m_container = container;

        addRequirements(m_container.shooter);
    }
    
    @Override
    public void initialize() {
        m_container.shooter.setFeeder(true);
    }
}
