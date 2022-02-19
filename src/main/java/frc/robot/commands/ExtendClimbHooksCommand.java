package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ExtendClimbHooksCommand extends CommandBase {
    private final RobotContainer container;
    public ExtendClimbHooksCommand(RobotContainer container) {
        this.container = container;

        addRequirements(this.container.climb);
    }

    @Override
    public void initialize() {
        this.container.climb.extendClimbHooks();
    }
}
