package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ColorSensor.ColorType;

public class PositionBallCommand extends CommandBase {
    RobotContainer m_container;

    public PositionBallCommand(RobotContainer container) {
        m_container = container;
 
        addRequirements(m_container.cargoIndexer);
    }

    @Override
    public void execute() {
        if(m_container.colorSensor.readColor() != ColorType.None) {
            m_container.cargoIndexer.setSpeed(0);
        }
        else {
            m_container.cargoIndexer.turnOnToIndex();
        }
    }
}
