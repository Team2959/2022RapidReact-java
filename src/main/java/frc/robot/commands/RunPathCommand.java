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
        PathPlannerTrajectory trajectory = PathPlanner.loadPath(path, 1, 0.5);
        container.drivetrain.resetOdometry(trajectory.getInitialPose());
        PPSwerveControllerCommand command = new PPSwerveControllerCommand(trajectory,
                () -> container.drivetrain.getPose(),
                container.drivetrain.getKinematics(),
                new PIDController(3, 0, 0),
                new PIDController(3, 0, 0),
                new ProfiledPIDController(3, 0, 0, new Constraints(Math.PI, Math.PI)),
                (SwerveModuleState[] states) -> container.drivetrain.setDesiredState(states),
                container.drivetrain);
        addCommands(command);
    }

}