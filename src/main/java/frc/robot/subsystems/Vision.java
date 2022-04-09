package frc.robot.subsystems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import cwtech.util.BasicTrajectory;
import edu.wpi.first.math.Pair;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
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

    private double kShootingMin;
    private double kShootingMax;
    private double kShootingRes;
    private Vector<Pair<Double, Double>> kShootingMap;

    public Vision() {
        m_txEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
        m_tyEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(Filesystem.getDeployDirectory().toPath().resolve("shooting.csv").toString()));
            boolean setMin = false;
            boolean setRes = false;
            boolean readHeader = false;
            String line;
            kShootingMap = new Vector<>();
            double lastDist = 0;

            while((line = reader.readLine()) != null) {
                if(!readHeader) {
                    readHeader = true;
                    continue;
                }
                String[] info = line.split(",");
                lastDist = Double.parseDouble(info[0]);
                if(setMin && !setRes) {
                    setRes = true;
                    kShootingRes = lastDist - kShootingMin;
                }
                if(!setMin) {
                    setMin = true;
                    kShootingMin = lastDist;
                }
                kShootingMap.add(new Pair<>(Double.parseDouble(info[1]), Double.parseDouble(info[2])));
            }
            kShootingMax = lastDist;
        }
        catch(Exception e) {
            kShootingMax = 0;
            kShootingMin = 0;
            kShootingRes = 0;
            kShootingMap = new Vector<>();
            DriverStation.reportError("Failed to load shooting map file", e.getStackTrace());
        }

        System.err.println(String.format("Min: %f, Max: %f, Res: %f", kShootingMin, kShootingMax, kShootingRes));
        System.err.println(String.format("Best Index for 0.60: %d", getClosestIndex(0.60)));
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

    public int getClosestIndex(double distance) {
        if(distance > kShootingMax) { 
            return kShootingMap.size();
        }
        else if(distance < kShootingMin) {
            return 0;
        }
        return (int) ((Math.round(distance / kShootingRes)) + 1 - kShootingMin);
    }

    public double getShooterSpeed(int index) {
        return kShootingMap.get(index).getFirst();
    }

    public double getHoodPosition(int index) {
        return kShootingMap.get(index).getSecond();
    }
}
