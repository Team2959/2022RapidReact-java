package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class FeedBallCommand extends InstantCommand {
    
    private final RobotContainer container;

    public FeedBallCommand(RobotContainer container) {
        this.container = container;
    }
    
    @Override
    public void initialize() {
        this.container.shooter.setFeeder(true);
    }
}
