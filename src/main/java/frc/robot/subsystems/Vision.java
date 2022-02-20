package frc.robot.subsystems;

import cwtech.util.Trajectory;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
    private final NetworkTableEntry txEntry;
    private final NetworkTableEntry tyEntry;

    /** This is what the limelight's height when mounted */
    private static final double kCameraHeightMeters = 1;
    /** This is what the limelight's angle when mounted */
    private static final double kCameraAngleDegrees = 0;

    public Vision() {
        this.txEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
        this.tyEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");
    }

    /** Gets the 'tx' from the limelight 
     * @return Horizontal offset from crosshair to target -29.8 to 29.8 degrees
    */
    public double getTX() {
        return (double) this.txEntry.getNumber(0.0);
    }

    /** Gets the 'ty' from the limelight 
     * @return Vertical offset from cross to target -24.85 to 24.85 degrees
    */
    public double getTY() {
        return (double) this.tyEntry.getNumber(0.0);
    }

    /** Calculates distance from a target.
     * 
     * @param heightMeters Height of the target in meters
     * @return Distance from target in meters
    */
    public double getDistanceFromTargetWithHeight(double heightMeters) {
        double a2 = getTX();
        return (heightMeters - kCameraHeightMeters) / (Trajectory.tan(kCameraAngleDegrees) + a2);
    }
}
