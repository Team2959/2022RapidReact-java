package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class IntakeToggleCommand extends InstantCommand {

    private final RobotContainer container;

    public IntakeToggleCommand(RobotContainer container) {
        this.container = container;
    
        addRequirements(this.container.intake);
    }

    @Override
    public void initialize() {
        this.container.intake.toggleIntake();
    }
}
