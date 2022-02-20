package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ReverseIntakeCommand extends CommandBase {
    private final RobotContainer container;

    public ReverseIntakeCommand(RobotContainer container) {
        this.container = container;
    
        addRequirements(this.container.intake);
    }

    @Override
    public void initialize() {
        this.container.intake.reverseIntake();
    }

    @Override
    public void end(boolean interupt) {
        this.container.intake.restoreIntakeDirection();
    }
}
