package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class IntakeToggleCommand extends CommandBase {

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
