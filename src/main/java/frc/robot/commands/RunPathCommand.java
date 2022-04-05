package frc.robot.commands;

// import com.pathplanner.lib.PathPlanner;
// import com.pathplanner.lib.PathPlannerTrajectory;
// import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.RobotContainer;

public class RunPathCommand extends SequentialCommandGroup {
    public RunPathCommand(RobotContainer container, String path) {
        // PathPlannerTrajectory trajectory = PathPlanner.loadPath(path, 1, 0.5);
        // Trajectory trajectory = PathPlanner.loadPath(path, 1, 0.5);
        try {
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("pathweaver/output/" + path + ".wpilib.json"));
            container.drivetrain.resetOdometry(trajectory.getInitialPose());
            SwerveControllerCommand command = new SwerveControllerCommand(trajectory,
                    () -> container.drivetrain.getPose(),
                    container.drivetrain.getKinematics(),
                    new PIDController(0.1, 0.0001, 0),
                    new PIDController(0.2, 0.0001, 0),
                    new ProfiledPIDController(0.4, 0.0005, 0, new Constraints(Math.PI, Math.PI)),
                    (SwerveModuleState[] states) -> container.drivetrain.setDesiredState(states),
                    container.drivetrain);
            addCommands(command);
        }
        catch(Exception e) {
            DriverStation.reportError("Failed to load path", e.getStackTrace());
        }
    }

}