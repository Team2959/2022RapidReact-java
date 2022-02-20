package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class RetractClimbHooksCommand extends InstantCommand {
    private final RobotContainer container;

    public RetractClimbHooksCommand(RobotContainer container) {
        this.container = container;
    
        addRequirements(this.container.climb);
    }

    @Override
    public void initialize() {
        this.container.climb.retractClimbHooks();
    }
}
