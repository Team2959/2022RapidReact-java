package frc.robot.commands;

import cwtech.util.Trajectory;
import cwtech.util.Trajectory.TrajectoryCalculation;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.RobotContainer;

public class TuneShooterAndHoodCommand extends ParallelCommandGroup {
    public TuneShooterAndHoodCommand(RobotContainer container) {
        // TODO get actual values
        TrajectoryCalculation calculation = Trajectory.calculate(-69, container.vision.getDistanceFromTargetWithHeight(3), 2);
        addCommands(
            new SetHoodAngleCommand(container, calculation.shootingAngleDegrees / 90),
            new SetShooterSpeedCommand(container, calculation.exitVelocityMetersPerSecond * 10) // TODO actual calculations
        );
    }
}
