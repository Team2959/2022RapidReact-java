package frc.robot.subsystems;

import cwtech.util.BasicTrajectory;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
    private final NetworkTableEntry m_txEntry;
    private final NetworkTableEntry m_tyEntry;

    /** This is what the limelight's height when mounted */
    private static final double kCameraHeightMeters = 1;
    /** This is what the limelight's angle when mounted */
    private static final double kCameraAngleDegrees = 0;

    public Vision() {
        m_txEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
        m_tyEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");
    }

    /** Gets the 'tx' from the limelight 
     * @return Horizontal offset from crosshair to target -29.8 to 29.8 degrees
    */
    public double getTX() {
        return (double) m_txEntry.getNumber(0.0);
    }

    /** Gets the 'ty' from the limelight 
     * @return Vertical offset from cross to target -24.85 to 24.85 degrees
    */
    public double getTY() {
        return (double) m_tyEntry.getNumber(0.0);
    }

    /** Calculates distance from a target.
     * 
     * @param heightMeters Height of the target in meters
     * @return Distance from target in meters
    */
    public double getDistanceFromTargetWithHeight(double heightMeters) {
        double a2 = getTX();
        return (heightMeters - kCameraHeightMeters) / (BasicTrajectory.tan(kCameraAngleDegrees) + a2);
    }
}
