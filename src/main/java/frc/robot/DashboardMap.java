package frc.robot;

public class DashboardMap {
    public static final String kDrivetrain = "Drivetrain";
    public static final String kDrivetrainDriveP = key("Drive P", kDrivetrain);
    public static final String kDrivetrainDriveI = key("Drive I", kDrivetrain);
    public static final String kDrivetrainDriveD = key("Drive D", kDrivetrain);
    public static final String kDrivetrainDriveFF = key("Drive FF", kDrivetrain);
    public static final String kDrivetrainTurnP = key("Turn P", kDrivetrain);
    public static final String kDrivetrainTurnI = key("Turn I", kDrivetrain);
    public static final String kDrivetrainTurnD = key("Turn D", kDrivetrain);
    public static final String kDrivetrainTurnFF = key("Turn FF", kDrivetrain);

    public static final String kOI = "OI";
    public static final String kOIXDeadband = key("X Deadband", kOI);
    public static final String kOIXExponent = key("X Exponent", kOI);
    public static final String kOIYDeadband = key("Y Deadband", kOI);
    public static final String kOIYExponent = key("Y Exponent", kOI);
    public static final String kOITurnDeadband = key("Turn Deadband", kOI);
    public static final String kOITurnExponent = key("Turn Exponent", kOI);

    public static final String kHood = "Hood";
    public static final String kHoodUseManualAngle = key("Use Specified Angle", kHood);
    public static final String kHoodManualAngle = key("Set Angle", kHood);

    public static final String kIntakeSpeed = "Intake/Speed";

    public static final String kTurret = "Turret";
    public static final String kTurretUseManualAngle = key("Use Specified Angle", kTurret);
    public static final String kTurretManualAngle = key("Set Angle", kTurret);

    public static final String kShooter = "Shooter";
    public static final String kShooterUseManualSpeed = key("Use Specified Speed", kShooter);
    public static final String kShooterManualSpeed = key("Set Speed", kShooter);
    public static final String kShooterAcceleratorSpeed = key("Accelarator Speed", kShooter);

    public static final String kTrajectory = "Trajectory";
    public static final String kTrajectoryTyOffset = key("TY Offset", kTrajectory);

    private static String key(String key, String parent) {
        if(parent == null) {
            return key;
        }
        return parent + '/' + key;
    }
}
