package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.OI.DriveState;

// Java do code sa ateema

public class TeleopDriveCommand extends CommandBase {

    private final boolean m_fieldRelative;
    private final RobotContainer m_container;

    public TeleopDriveCommand(RobotContainer container, boolean fieldRelative) {
        m_fieldRelative = fieldRelative;
        m_container = container;

        addRequirements(m_container.drivetrain);
    }

    @Override
    public void execute() {
        DriveState state = m_container.oi.getDriveState();
        m_container.drivetrain.drive(state.m_xMetersPerSecond, state.m_yMetersPerSecond, state.m_rotationRadiansPerSecond, SmartDashboard.getBoolean("Field Centric", true));
    }

    @Override
    public void end(boolean interupted) {
        m_container.drivetrain.drive(0, 0, 0, m_fieldRelative);
    }
}
