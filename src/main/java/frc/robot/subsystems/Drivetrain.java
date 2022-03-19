package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.swervedrivespecialties.swervelib.Mk3SwerveModuleHelper;
import com.swervedrivespecialties.swervelib.SwerveModule;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
    private static final double kTrackwidthMeters = 0.381 * 2;
    private static final double kWheelbaseMeters = kTrackwidthMeters;
    private static final double kMaxVoltage = 12.0;
    public static final double kVelocityMetersPerSecond = 4.14528;
    public static final double kMaxAngularVelocityRadiasPerSecond = kVelocityMetersPerSecond /
            Math.hypot(kTrackwidthMeters / 2.0, kWheelbaseMeters / 2.0);

    private final SwerveModule m_frontLeftModule;
    private final SwerveModule m_frontRightModule;
    private final SwerveModule m_backLeftModule;
    private final SwerveModule m_backRightModule;

    private final AHRS m_gyroscope = new AHRS(SPI.Port.kMXP);

    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            new Translation2d(kTrackwidthMeters / 2.0, kWheelbaseMeters / 2.0),
            new Translation2d(kTrackwidthMeters / 2.0, -kWheelbaseMeters / 2.0),
            new Translation2d(-kTrackwidthMeters / 2.0, kWheelbaseMeters / 2.0),
            new Translation2d(-kTrackwidthMeters / 2.0, -kWheelbaseMeters / 2.0)
    );
    private final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(kinematics, Rotation2d.fromDegrees(m_gyroscope.getFusedHeading()));

    private ChassisSpeeds chassisSpeeds = new ChassisSpeeds(0.0, 0.0, 0.0);

    public Drivetrain() {
        ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("Drivetrain");

        m_frontLeftModule = Mk3SwerveModuleHelper.createNeo(
                shuffleboardTab.getLayout("Front Left Module", BuiltInLayouts.kList)
                        .withSize(2, 4)
                        .withPosition(0, 0),
                Mk3SwerveModuleHelper.GearRatio.STANDARD,
                RobotMap.kFrontLeftDriveCANSparkMaxMotor,
                RobotMap.kFrontLeftTurnCANSparkMaxMotor,
                RobotMap.kFrontLeftTurnPulseWidthDigIO,
                RobotMap.kZeroedFrontLeft
        );

        m_frontRightModule = Mk3SwerveModuleHelper.createNeo(
                shuffleboardTab.getLayout("Front Right Module", BuiltInLayouts.kList)
                        .withSize(2, 4)
                        .withPosition(2, 0),
                Mk3SwerveModuleHelper.GearRatio.STANDARD,
                RobotMap.kFrontRightDriveCANSparkMaxMotor,
                RobotMap.kFrontRightTurnCANSparkMaxMotor,
                RobotMap.kFrontRightTurnPulseWidthDigIO,
                RobotMap.kZeroedFrontRight
        );

        m_backLeftModule = Mk3SwerveModuleHelper.createNeo(
                shuffleboardTab.getLayout("Back Left Module", BuiltInLayouts.kList)
                        .withSize(2, 4)
                        .withPosition(4, 0),
                Mk3SwerveModuleHelper.GearRatio.STANDARD,
                RobotMap.kBackLeftDriveCANSparkMaxMotor,
                RobotMap.kBackLeftTurnCANSparkMaxMotor,
                RobotMap.kBackLeftTurnPulseWidthDigIO,
                RobotMap.kZeroedBackLeft
        );

        m_backRightModule = Mk3SwerveModuleHelper.createNeo(
                shuffleboardTab.getLayout("Back Right Module", BuiltInLayouts.kList)
                        .withSize(2, 4)
                        .withPosition(6, 0),
                Mk3SwerveModuleHelper.GearRatio.STANDARD,
                RobotMap.kBackRightDriveCANSparkMaxMotor,
                RobotMap.kBackRightTurnCANSparkMaxMotor,
                RobotMap.kBackRightTurnPulseWidthDigIO,
                RobotMap.kZeroedBackRight
        );

        shuffleboardTab.addNumber("Gyroscope Angle", () -> getRotation().getDegrees());
        shuffleboardTab.addNumber("Pose X", () -> m_odometry.getPoseMeters().getX());
        shuffleboardTab.addNumber("Pose Y", () -> m_odometry.getPoseMeters().getY());
    }

    public void zeroGyroscope() {
        m_odometry.resetPosition(
                new Pose2d(m_odometry.getPoseMeters().getTranslation(), Rotation2d.fromDegrees(0.0)),
                Rotation2d.fromDegrees(m_gyroscope.getFusedHeading())
        );
    }

    public Rotation2d getRotation() {
        return m_odometry.getPoseMeters().getRotation();
    }

    private double m_xSpeed, m_ySpeed, m_rotSpeed;
    private boolean m_fieldCentric;

    public void drive(double xSpeed, double ySpeed, double rotSpeed, boolean fieldCentric) {
        m_xSpeed = xSpeed;
        m_ySpeed = ySpeed;
        m_rotSpeed = rotSpeed;
        m_fieldCentric = fieldCentric;
    }

    public void drive(ChassisSpeeds chassisSpeeds) {
        this.chassisSpeeds = chassisSpeeds;
    }

    @Override
    public void periodic() {
        m_odometry.update(Rotation2d.fromDegrees(m_gyroscope.getFusedHeading()),
                new SwerveModuleState(m_frontLeftModule.getDriveVelocity(), new Rotation2d(m_frontLeftModule.getSteerAngle())),
                new SwerveModuleState(m_frontRightModule.getDriveVelocity(), new Rotation2d(m_frontRightModule.getSteerAngle())),
                new SwerveModuleState(m_backLeftModule.getDriveVelocity(), new Rotation2d(m_backLeftModule.getSteerAngle())),
                new SwerveModuleState(m_backRightModule.getDriveVelocity(), new Rotation2d(m_backRightModule.getSteerAngle()))
        );

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(m_fieldCentric
        ? ChassisSpeeds.fromFieldRelativeSpeeds(m_xSpeed, m_ySpeed, m_rotSpeed,
                m_gyroscope.getRotation2d())
        : new ChassisSpeeds(m_xSpeed, m_ySpeed, m_rotSpeed));

        m_frontLeftModule.set(states[0].speedMetersPerSecond / kVelocityMetersPerSecond * kMaxVoltage, states[0].angle.getRadians());
        m_frontRightModule.set(states[1].speedMetersPerSecond / kVelocityMetersPerSecond * kMaxVoltage, states[1].angle.getRadians());
        m_backLeftModule.set(states[2].speedMetersPerSecond / kVelocityMetersPerSecond * kMaxVoltage, states[2].angle.getRadians());
        m_backRightModule.set(states[3].speedMetersPerSecond / kVelocityMetersPerSecond * kMaxVoltage, states[3].angle.getRadians());
    }
}