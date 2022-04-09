package frc.robot.subsystems;

import cwtech.util.BasicTrajectory;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardMap;

public class Vision extends SubsystemBase {
    private final NetworkTableEntry m_txEntry;
    private final NetworkTableEntry m_tyEntry;

    /** This is what the limelight's height when mounted */
    public static final double kCameraHeightMeters = 43.75 * 0.0254;
    /** This is what the limelight's angle when mounted */
    private static final double kCameraAngleDegrees = 30;

    public static final double kCameraTYOffset = 5.5;
    private static final double kHubRadius = (26 * 0.0254);
    public static final double kHubHeightMeters = (104 * 0.0254);
    public static final double kDifferenceMeters = kHubHeightMeters - kCameraHeightMeters;

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
        return (double) m_tyEntry.getNumber(0.0) - SmartDashboard.getNumber(DashboardMap.kTrajectoryTyOffset, Vision.kCameraTYOffset);
    }

    /** Calculates distance from a target.
     * 
     * @param heightMeters Height of the target in meters
     * @return Distance from target in meters
    */
    private double getDistanceFromTargetWithHeight(double heightMeters) {
        double a2 = getTY();
        return (heightMeters - kCameraHeightMeters) / BasicTrajectory.tan(kCameraAngleDegrees + a2);
    }

    public double getDistanceToHubCenterWithHeight(double heightMeters) {
        return getDistanceFromTargetWithHeight(heightMeters) + kHubRadius;
    }

    public void setLedMode(int mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(mode);
    }

    public int ledMode() {
        return (int) NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").getNumber(0);
    }

    @Override
    public void periodic() {
        if(ledMode() == 2) { //if force blink
            return;
        }
        else if(DriverStation.isEnabled()) {
            setLedMode(0); //normal
        }
        else {
            setLedMode(1); //force off
        }
    }
}
