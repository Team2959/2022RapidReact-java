package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class ReverseAccelaratorCommand extends CommandBase {
    private final RobotContainer m_container;
    public ReverseAccelaratorCommand(RobotContainer container) {
        m_container = container;
    
        addRequirements(m_container.shooter);
    }

    @Override
    public void initialize() {
        m_container.shooter.setAccelarator(-Shooter.kAcceleratorSpeed);
    }

    @Override 
    public void end(boolean i) {
        m_container.shooter.setAccelarator(0.0);
    }
}
