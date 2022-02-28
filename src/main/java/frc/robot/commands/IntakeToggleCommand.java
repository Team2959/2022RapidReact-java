package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class IntakeToggleCommand extends InstantCommand {

    private final RobotContainer m_container;

    public IntakeToggleCommand(RobotContainer container) {
        m_container = container;
    
        addRequirements(m_container.intake);
    }

    @Override
    public void initialize() {
        m_container.intake.toggleIntake();
    }
}
