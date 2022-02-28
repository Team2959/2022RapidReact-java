package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {

    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;


    private static final Translation2d kFrontLeftLocation = new Translation2d(+0.381, -0.381);
    private static final Translation2d kFrontRightLocation = new Translation2d(-0.381, -0.381);
    private static final Translation2d kBackLeftLocation = new Translation2d(+0.381, +0.381);
    private static final Translation2d kBackRightLocation = new Translation2d(-0.381, +0.381);


    private SwerveModule m_frontLeft;
    private SwerveModule m_frontRight;
    private SwerveModule m_backLeft;
    private SwerveModule m_backRight;

    private AHRS m_navX;

    private SwerveDriveKinematics m_kinematics;
    private SwerveDriveOdometry m_odometry;

    public void resetNavX() {
        m_navX.reset();
    }

    public Drivetrain() {
        m_navX = new AHRS(Port.kMXP);

        m_frontLeft = new SwerveModule(RobotMap.kFrontLeftDriveCANSparkMaxMotor,
                RobotMap.kFrontLeftTurnCANSparkMaxMotor, RobotMap.kFrontLeftTurnPulseWidthDigIO,
                RobotMap.kZeroedFrontLeft, "Front Left");
        m_frontRight = new SwerveModule(RobotMap.kFrontRightDriveCANSparkMaxMotor,
                RobotMap.kFrontRightTurnCANSparkMaxMotor, RobotMap.kFrontRightTurnPulseWidthDigIO,
                RobotMap.kZeroedFrontRight, "Front Right");
        m_backLeft = new SwerveModule(RobotMap.kBackLeftDriveCANSparkMaxMotor,
                RobotMap.kBackLeftTurnCANSparkMaxMotor, RobotMap.kBackLeftTurnPulseWidthDigIO,
                RobotMap.kZeroedBackLeft, "Back Left");
        m_backRight = new SwerveModule(RobotMap.kBackRightDriveCANSparkMaxMotor,
                RobotMap.kBackRightTurnCANSparkMaxMotor, RobotMap.kBackRightTurnPulseWidthDigIO,
                RobotMap.kZeroedBackRight, "Back Right");

        m_kinematics = new SwerveDriveKinematics(kFrontLeftLocation, kFrontRightLocation, kBackLeftLocation,
                kBackRightLocation);
        m_odometry = new SwerveDriveOdometry(m_kinematics, m_navX.getRotation2d());
    }

    public void drive(double xMetersPerSecond, double yMetersPerSecond, double rotationRadiansPerSecond,
            boolean fieldRelative) {
        SwerveModuleState[] states = m_kinematics.toSwerveModuleStates(fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xMetersPerSecond, yMetersPerSecond, rotationRadiansPerSecond,
                        m_navX.getRotation2d())
                : new ChassisSpeeds(xMetersPerSecond, yMetersPerSecond, rotationRadiansPerSecond));

        SmartDashboard.putNumber("X Meters Per Second", xMetersPerSecond);
        SmartDashboard.putNumber("Y Meters Per Second", yMetersPerSecond);
        SmartDashboard.putNumber("Rot Radians Per Second", rotationRadiansPerSecond);

        SwerveModuleState fl = states[0];
        SwerveModuleState fr = states[1];
        SwerveModuleState bl = states[2];
        SwerveModuleState br = states[3];

        m_frontLeft.setDesiredState(fl);
        m_frontRight.setDesiredState(fr);
        m_backLeft.setDesiredState(bl);
        m_backRight.setDesiredState(br);
    }

    //
    @Override
    public void periodic() {
        m_odometry.update(m_navX.getRotation2d(), m_frontLeft.getState(), m_frontRight.getState(),
                m_backLeft.getState(), m_backRight.getState());

        m_frontLeft.periodic();
        m_frontRight.periodic();
        m_backLeft.periodic();
        m_backRight.periodic();
    }

    public void setInitalPositions() {
        m_frontLeft.setInitalPosition();
        m_frontRight.setInitalPosition();
        m_backLeft.setInitalPosition();
        m_backRight.setInitalPosition();
    }

    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Drivetrain");
        builder.addDoubleProperty("X", () -> m_odometry.getPoseMeters().getX(), null);
        builder.addDoubleProperty("Y", () -> m_odometry.getPoseMeters().getY(), null);
    }

}

// I'll be back in a little bit