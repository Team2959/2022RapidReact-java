package frc.robot.commands;

import cwtech.util.BasicTrajectory;
import cwtech.util.BasicTrajectory.TrajectoryCalculation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import cwtech.util.Util;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class SetShooterSpeedCommand extends CommandBase {
    private final RobotContainer m_container;
    private double m_speed = 0;

    public SetShooterSpeedCommand(RobotContainer container) {
        m_container = container;

        addRequirements(m_container.shooter);
    }

    @Override
    public void initialize() {
        double distanceMeters = m_container.vision.getDistanceFromTargetWithHeight(Vision.kHubHeightMeters) + Vision.kHubRadius;
        TrajectoryCalculation calculation = BasicTrajectory.calculate(-70, distanceMeters, Vision.kDifferenceMeters);
        SmartDashboard.putNumber("Trajectory/Hood Angle", calculation.m_shootingAngleDegrees);
        SmartDashboard.putNumber("Trajectory/Exit Velocity", calculation.m_exitVelocityMetersPerSecond);
        double rpm = (calculation.m_exitVelocityMetersPerSecond / (2 * Math.PI * Shooter.kWheelRadius)) * 60.0;
        SmartDashboard.putNumber("Trajectory/RPMs", rpm);
        SmartDashboard.putNumber("Trajectory/Distance", distanceMeters);
        double ty = m_container.vision.getTY();
        SmartDashboard.putNumber("Trajectory/Corrected TY", ty);
        m_speed = rpm;
        
        //m_container.shooter.setVelocity(m_speed);
        //m_container.shooter.setAccelaratorVelocity(m_speed * 1.3);
        
        m_container.shooter.setVelocity(m_speed);
        m_container.shooter.setAccelarator(m_speed > 0 ? SmartDashboard.getNumber("Accelarator Speed", 0.85) : 0.0);
    }

    @Override
    public boolean isFinished() {
        return Util.dcompareMine(m_container.shooter.getVelocity(), m_speed, 500);
        // return true;
    }
}
