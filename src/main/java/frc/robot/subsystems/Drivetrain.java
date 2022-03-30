package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.DashboardMap;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
    public static final double kMaxSpeedMetersPerSecond = 4;
    public static final double kMaxAngularSpeedRadiansPerSecond = kMaxSpeedMetersPerSecond / Math.hypot(0.381, 0.381);
    
    private final Translation2d kFrontLeftLocation = new Translation2d(0.381, 0.381);
    private final Translation2d kFrontRightLocation = new Translation2d(0.381, -0.381);
    private final Translation2d kBackLeftLocation = new Translation2d(-0.381, 0.381);
    private final Translation2d kBackRightLocation = new Translation2d(-0.381, -0.381);


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

    public void onDisabledInit() {
        m_frontLeft.onDisabledInit();
        m_frontRight.onDisabledInit();
        m_backLeft.onDisabledInit();
        m_backRight.onDisabledInit();
    }

    public void setDesiredState(SwerveModuleState[] states) {
        SmartDashboard.putNumber("SM/MPS", states[0].speedMetersPerSecond);
        m_frontLeft.setDesiredState(states[0]);
        m_frontRight.setDesiredState(states[1]);
        m_backLeft.setDesiredState(states[2]);
        m_backRight.setDesiredState(states[3]);
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

        SwerveDriveKinematics.desaturateWheelSpeeds(states, kMaxSpeedMetersPerSecond);

        SwerveModuleState fl = states[0];
        SwerveModuleState fr = states[1];
        SwerveModuleState bl = states[2];
        SwerveModuleState br = states[3];

        SmartDashboard.putNumber("SM/MPS", fl.speedMetersPerSecond);

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

        var pos = m_odometry.getPoseMeters();
        SmartDashboard.putNumber("Odometry/X", pos.getX());
        SmartDashboard.putNumber("Odometry/Y", pos.getY());
    }

    public void setInitalPositions() {
        m_frontLeft.setInitalPosition();
        m_frontRight.setInitalPosition();
        m_backLeft.setInitalPosition();
        m_backRight.setInitalPosition();
    }

    public SwerveDriveKinematics getKinematics() {
        return m_kinematics;
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public double getGyroAngle() {
        return m_navX.getAngle();
    }

    public Rotation2d getHeading() {
        return m_navX.getRotation2d();
    }

    public double getGyroRate() {
        return m_navX.getRate();
    }

    public void resetOdometry(Pose2d pose) {
        m_odometry.resetPosition(pose, m_navX.getRotation2d());
    }
}
