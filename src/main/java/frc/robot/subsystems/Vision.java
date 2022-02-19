package frc.robot.subsystems;

import cwtech.util.Trajectory;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
    private final NetworkTableEntry txEntry;
    private static final double kCameraHeightMeters = 1;
    private static final double kCameraAngleDegrees = 0;

    public Vision() {
        this.txEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
    }

    public double getTX() {
        return (double) this.txEntry.getNumber(0.0);
    }

    public double getDistanceFromTargetWithHeight(double heightMeters) {
        double a2 = getTX();
        return (heightMeters - kCameraHeightMeters) / (Trajectory.tan(kCameraAngleDegrees) + a2);
    }
}
