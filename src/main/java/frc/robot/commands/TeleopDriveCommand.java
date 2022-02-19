package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.OI.DriveState;
import frc.robot.OI.DriveType;

// Java do code sa ateema

public class TeleopDriveCommand extends CommandBase {

    private final boolean fieldRelative;
    private final RobotContainer container;

    public TeleopDriveCommand(RobotContainer container, boolean fieldRelative) {
        this.fieldRelative = fieldRelative;
        this.container = container;

        addRequirements(this.container.drivetrain);
    }

    @Override
    public void execute() {
        DriveState state = this.container.oi.getDriveState(DriveType.Double);
        this.container.drivetrain.drive(state.xMetersPerSecond, state.yMetersPerSecond, state.rotationRadiansPerSecond, this.fieldRelative);
    }

    @Override
    public void end(boolean interupted) {
        this.container.drivetrain.drive(0, 0, 0, this.fieldRelative);
    }
}
