package frc.robot.commands;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class RunPathCommand extends SequentialCommandGroup {
    public RunPathCommand(RobotContainer container, String path) {
        PathPlannerTrajectory trajectory = PathPlanner.loadPath(path, 8, 5);
        PPSwerveControllerCommand command = new PPSwerveControllerCommand(trajectory,
                () -> container.drivetrain.getPose(),
                container.drivetrain.getKinematics(),
                new PIDController(1, 0, 0),
                new PIDController(1, 0, 0),
                new ProfiledPIDController(1, 0, 0, new Constraints(8, 5)),
                (SwerveModuleState[] states) -> container.drivetrain.setDesiredState(states),
                container.drivetrain);
        addCommands(command);
    }

}
