package frc.robot.commands;

import cwtech.util.BasicTrajectory;
import cwtech.util.BasicTrajectory.TrajectoryCalculation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import cwtech.util.Util;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DashboardMap;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class SetShooterSpeedCommand extends CommandBase {
    private final RobotContainer m_container;
    private double m_targetRpm = 0;
    private double m_desiredRpm = -1.0;

    public SetShooterSpeedCommand(RobotContainer container) {
        m_container = container;

        addRequirements(m_container.shooter);
    }

    public SetShooterSpeedCommand(RobotContainer container, double desiredRpm) {
        m_container = container;
        m_desiredRpm = desiredRpm;

        addRequirements(m_container.shooter);
    }

    @Override
    public void initialize() {
        if (m_desiredRpm >= 0.0)
        {
            m_targetRpm = m_desiredRpm;
        }
        else if (SmartDashboard.getBoolean(DashboardMap.kShooterUseManualSpeed, false))
        {
            m_targetRpm = SmartDashboard.getNumber(DashboardMap.kShooterManualSpeed, 0.0);
        }
        else
        {
            double distanceMeters = m_container.vision.getDistanceToHubCenterWithHeight(Vision.kHubHeightMeters);
            TrajectoryCalculation calculation = BasicTrajectory.calculate(SmartDashboard.getNumber("Shooter/Entry Angle", -70), distanceMeters, Vision.kDifferenceMeters);
            SmartDashboard.putNumber("Trajectory/Hood Angle", calculation.m_shootingAngleDegrees);
            SmartDashboard.putNumber("Trajectory/Exit Velocity", calculation.m_exitVelocityMetersPerSecond);
            m_targetRpm = (calculation.m_exitVelocityMetersPerSecond / (2 * Math.PI * Shooter.kWheelRadius)) * 60.0;
            
            // Rough estimate (Possibly times .25)
            // m_targetRpm += 400;
            m_targetRpm *= SmartDashboard.getNumber("Shooter/Multi", 1.195);
            
            SmartDashboard.putNumber("Trajectory/RPMs", m_targetRpm);
            SmartDashboard.putNumber("Trajectory/Distance", distanceMeters);
            double ty = m_container.vision.getTY();
            SmartDashboard.putNumber("Trajectory/Corrected TY", ty);
        }
        
        //m_container.shooter.setVelocity(m_targetRpm);
        //m_container.shooter.setAccelaratorVelocity(m_targetRpm * 1.3);
        
        /*
        if(!m_container.colorSensor.ballIsForTeam()) {
            m_targetRpm = 500;
        }
        */

        m_container.shooter.setVelocity(m_targetRpm);
        m_container.shooter.setAccelarator(m_targetRpm > 0 ? SmartDashboard.getNumber(DashboardMap.kShooterAcceleratorSpeed, Shooter.kAcceleratorSpeed) : 0.0);
    }

    @Override
    public boolean isFinished() {
        // return Util.dcompareMine(m_container.shooter.getVelocity(), m_targetRpm, 300);
        if (m_targetRpm > 300)
            return m_container.shooter.getVelocity() - 300 >= m_targetRpm;
        else
            return m_container.shooter.getVelocity() < m_targetRpm + 300;
    }
}
