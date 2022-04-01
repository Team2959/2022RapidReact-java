package frc.robot.commands;

import cwtech.util.BasicTrajectory;
import cwtech.util.BasicTrajectory.TrajectoryCalculation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DashboardMap;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class RecalculateShootingCommand extends CommandBase {
    RobotContainer m_container;

    double m_startVelocity = 0;
    int m_ticks = 0;

    public RecalculateShootingCommand(RobotContainer container) {
        m_container = container;
    }

    @Override
    public void initialize() {
        m_startVelocity = m_container.shooter.getVelocity();
        m_ticks = 0;
    }

    @Override
    public void execute() {
        if(m_ticks % 500 == 0) {
            double distanceMeters = m_container.vision.getDistanceToHubCenterWithHeight(Vision.kHubHeightMeters);
            TrajectoryCalculation calculation = BasicTrajectory.calculate(SmartDashboard.getNumber(DashboardMap.kShooterEntryAngle, Shooter.kShooterEntryAngle), distanceMeters, Vision.kDifferenceMeters);
            var targetRpm = (calculation.m_exitVelocityMetersPerSecond / (2 * Math.PI * Shooter.kWheelRadius)) * 60.0;
            targetRpm *= SmartDashboard.getNumber(DashboardMap.kShooterMulti, Shooter.kShooterMulti);
            if(Math.abs(targetRpm - m_startVelocity) > 500) {
                
            }
        }
    }
}

