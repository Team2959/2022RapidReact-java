package frc.robot.commands;

import cwtech.util.BasicTrajectory;
import cwtech.util.BasicTrajectory.TrajectoryCalculation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class TuneShooterAndHoodCommand extends ParallelCommandGroup {
    public TuneShooterAndHoodCommand(RobotContainer container) {
        // TODO get actual values
        TrajectoryCalculation calculation = BasicTrajectory.calculate(-70, container.vision.getDistanceFromTargetWithHeight(103 - Vision.kCameraHeightInches /* inches */), 2);
        // System.err.println(calculation);


        double rpm = (calculation.m_exitVelocityMetersPerSecond / (2 * Math.PI * Shooter.kWheelRadius)) * 60.0;

        SmartDashboard.putNumber("Exit Velocity", calculation.m_exitVelocityMetersPerSecond);
        SmartDashboard.putNumber("sHOOTER rPMS", rpm);

        //if(!container.shooter.getDashboardUseCalculations()) {
            rpm = container.shooter.getDashboardShooterSpeed();
        //}

        SmartDashboard.putNumber("Trajectory Hood Angle", calculation.m_shootingAngleDegrees);

        addCommands(
            //new SetHoodAngleCommand(container, container.shooter.getDashboardHoodAngle()),
            new SetShooterSpeedCommand(container,  SmartDashboard.getNumber("Shooter Speed", 3000)));
    }
}
