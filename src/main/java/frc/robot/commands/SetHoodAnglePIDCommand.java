package frc.robot.commands;
import cwtech.util.Util;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SetHoodAnglePIDCommand extends CommandBase {
    private RobotContainer m_container;
    private double m_position;
    public SetHoodAnglePIDCommand(RobotContainer container, double position) {
        m_container = container;
        m_position = position;
    }

    @Override
    public void initialize() {
        m_container.hood.setPosition(m_position);
    }

    @Override
    public boolean isFinished() {
        return Util.dcompareMine(m_container.hood.getPosition(), m_position, 0.01);
    }
}
