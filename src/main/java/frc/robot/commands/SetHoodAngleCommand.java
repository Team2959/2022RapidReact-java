package frc.robot.commands;

import cwtech.util.BasicTrajectory;
import cwtech.util.BasicTrajectory.TrajectoryCalculation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DashboardMap;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Vision;

public class SetHoodAngleCommand extends CommandBase {
    private final RobotContainer m_container;
    private double m_hoodPositionDegrees = 0.0;
    private double m_hoodPosition = 0.0;
    private boolean m_backwards;
    private final double kSpeed = 1.0;

    public SetHoodAngleCommand(RobotContainer container) { 
        m_container = container;
        m_backwards = false;

        addRequirements(m_container.hood);
    }

    public SetHoodAngleCommand(RobotContainer container, double positionInDegrees) {
        m_container = container;
        m_hoodPositionDegrees = positionInDegrees;
        m_backwards = false;

        addRequirements(m_container.hood);
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Hood Angle MESSAGE", "started");

        if (m_hoodPositionDegrees <= 0)
        {
            if(SmartDashboard.getBoolean(DashboardMap.kHoodUseManualAngle, false)) {
                m_hoodPositionDegrees = SmartDashboard.getNumber(DashboardMap.kHoodManualAngle, Hood.kMinDegrees);
                SmartDashboard.putNumber("Hood Target Debug - Raw Degrees", m_hoodPositionDegrees);
            }
            else {
                double distanceMeters = m_container.vision.getDistanceToHubCenterWithHeight(Vision.kHubHeightMeters);
                TrajectoryCalculation calculation = BasicTrajectory.calculate(SmartDashboard.getNumber("Shooter/Entry Angle", -70), distanceMeters, Vision.kDifferenceMeters);
                m_hoodPositionDegrees = calculation.m_shootingAngleDegrees;
                SmartDashboard.putNumber("Hood Target Angle From Vision", m_hoodPositionDegrees);
            }
        }
        m_hoodPosition = m_container.hood.convertToEncoderPositionFromDegrees(m_hoodPositionDegrees);

        SmartDashboard.putNumber("Hood Target Debug", m_hoodPosition);

        m_hoodPosition = Math.max(m_hoodPosition, m_container.hood.getMinEncoder());
        m_hoodPosition = Math.min(m_hoodPosition, m_container.hood.getMaxEncoder());

        SmartDashboard.putNumber("Hood Target Debug Limited", m_hoodPosition);
        double hoodPosition = m_container.hood.getPosition();
    
        SmartDashboard.putNumber("Hood Position Debug", hoodPosition);

        if(hoodPosition < m_hoodPosition) {
            m_container.hood.setSpeed(kSpeed);
            m_backwards = false;
        } 
        else {
            m_container.hood.setSpeed(-kSpeed);
            m_backwards = true;
        }
    }

    @Override
    public boolean isFinished() {
        double currentHoodPosition = m_container.hood.getPosition();

        // KFR:  This does not belong in isFinished if try to restore, put in execute override code
        // double diff = Math.abs(currentHoodPosition - m_hoodPosition);
        /*if(diff > 0.008) {
            if(m_backwards) {
                m_container.hood.setSpeed(kSpeed);
            } else {
                m_container.hood.setSpeed(-kSpeed);
            }
        }*/

        // Checks to see if at or pass requested hood position and if so stop
        return m_backwards ? currentHoodPosition <= m_hoodPosition : currentHoodPosition >= m_hoodPosition;
    }

    @Override
    public void end(boolean interrupted) {
        m_container.hood.setSpeed(0.0);
        SmartDashboard.putString("Shooter Speed MESSAGE", "ended");
    }
    
}
