package frc.robot.commands;

import cwtech.util.BasicTrajectory;
import cwtech.util.BasicTrajectory.TrajectoryCalculation;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.RobotContainer;

public class TuneShooterAndHoodCommand extends ParallelCommandGroup {
    public TuneShooterAndHoodCommand(RobotContainer container) {
        // TODO get actual values
        TrajectoryCalculation calculation = BasicTrajectory.calculate(-69, container.vision.getDistanceFromTargetWithHeight(3), 2);
        // System.err.println(calculation);
        addCommands(
            //new SetHoodAngleCommand(container, 0.5),
            new SetShooterSpeedCommand(container, 1750) // TODO actual calculations
        );
    }
}
