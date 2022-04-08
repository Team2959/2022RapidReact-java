package frc.robot.commands;

import cwtech.util.BasicTrajectory;
import cwtech.util.Util;
import cwtech.util.BasicTrajectory.TrajectoryCalculation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DashboardMap;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class SetShooterSpeedCommand extends CommandBase {
    private final RobotContainer m_container;
    private double m_targetFrontRPM = 0;
    private double m_targetBackRPM = 0;
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
        if (m_desiredRpm >= 0.0) {
            m_targetFrontRPM = m_desiredRpm;
        }
        else {
            double distanceMeters = m_container.vision.getDistanceToHubCenterWithHeight(Vision.kHubHeightMeters);
            TrajectoryCalculation calculation = BasicTrajectory.calculate(
                    SmartDashboard.getNumber(DashboardMap.kShooterEntryAngle, Shooter.kShooterEntryAngle),
                    distanceMeters, Vision.kDifferenceMeters);
            m_targetFrontRPM = (calculation.m_exitVelocityMetersPerSecond / (2 * Math.PI * Shooter.kWheelRadius))
                    * 60.0;
            m_targetBackRPM = (calculation.m_exitVelocityMetersPerSecond / (2 * Math.PI * Shooter.kBackWheelRadius))
                    * 60.0;
        }

        if(SmartDashboard.getBoolean(DashboardMap.kShooterDoManualBack, false)) {
            m_targetBackRPM = SmartDashboard.getNumber(DashboardMap.kShooterManualBack, 0.0);
        }

        if(SmartDashboard.getBoolean(DashboardMap.kShooterDoManualBack, false)) {
            m_targetFrontRPM = SmartDashboard.getNumber(DashboardMap.kShooterManualFront, 0.0);
        }

        m_targetFrontRPM *= SmartDashboard.getNumber(DashboardMap.kShooterMulti, 1.0);
        m_targetBackRPM *= SmartDashboard.getNumber(DashboardMap.kBackShooterMulti, 1.0);


        SmartDashboard.putNumber("Expected Front Velocity", m_targetFrontRPM);
        SmartDashboard.putNumber("Expected Back Velocity", m_targetBackRPM);

        m_container.shooter.setFrontVelocity(m_targetFrontRPM);
        m_container.shooter.setBackVelocity(m_targetBackRPM);
        m_container.shooter.setAccelarator(m_targetFrontRPM > 0 ? SmartDashboard.getNumber(DashboardMap.kShooterAcceleratorSpeed, Shooter.kAcceleratorSpeed) : 0.0);
    }

    @Override
    public boolean isFinished() {
        // return Util.dcompareMine(m_container.shooter.getVelocity(), m_targetRpm,
        // 300);
        // if (m_targetFrontRPM > 100)
        // return m_container.shooter.getFrontVelocity() >= m_targetFrontRPM - 100;
        // else
        // return m_container.shooter.getFrontVelocity() < m_targetFrontRPM + 600;
        return Util.dcompare2(m_container.shooter.getFrontVelocity(), m_targetFrontRPM, 300)
                && Util.dcompare2(m_container.shooter.getBackVelocity(), m_targetBackRPM, 300);
        // return true;
    }

    // @Override
    // public void end(boolean interrupted) {
    // SmartDashboard.putString("Shooter Speed MESSAGE", "ended");
    // }
}
