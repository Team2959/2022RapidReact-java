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
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.DashboardMap;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {

    public static final double kMaxSpeedMetersPerSecond = 12;
    public static final double kMaxAngularSpeedRadiansPerSecond = 4 * Math.PI;


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

    public void onDisabledInit() {
        // m_frontLeft.getDriveController().setP(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveP, 0.0));
        // m_frontLeft.getDriveController().setI(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveI, 0.0));
        // m_frontLeft.getDriveController().setD(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveD, 0.0));
        // m_frontLeft.getDriveController().setFF(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveFF, 0.0));
        // m_frontLeft.getTurnController().setP(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnP, 0.0));
        // m_frontLeft.getTurnController().setI(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnI, 0.0));
        // m_frontLeft.getTurnController().setD(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnD, 0.0));
        // m_frontLeft.getTurnController().setFF(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnFF, 0.0));
    
        // m_frontRight.getDriveController().setP(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveP, 0.0));
        // m_frontRight.getDriveController().setI(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveI, 0.0));
        // m_frontRight.getDriveController().setD(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveD, 0.0));
        // m_frontRight.getDriveController().setFF(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveFF, 0.0));
        // m_frontRight.getTurnController().setP(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnP, 0.0));
        // m_frontRight.getTurnController().setI(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnI, 0.0));
        // m_frontRight.getTurnController().setD(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnD, 0.0));
        // m_frontRight.getTurnController().setFF(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnFF, 0.0));

        // m_backLeft.getDriveController().setP(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveP, 0.0));
        // m_backLeft.getDriveController().setI(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveI, 0.0));
        // m_backLeft.getDriveController().setD(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveD, 0.0));
        // m_backLeft.getDriveController().setFF(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveFF, 0.0));
        // m_backLeft.getTurnController().setP(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnP, 0.0));
        // m_backLeft.getTurnController().setI(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnI, 0.0));
        // m_backLeft.getTurnController().setD(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnD, 0.0));
        // m_backLeft.getTurnController().setFF(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnFF, 0.0));
    
        // m_backRight.getDriveController().setP(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveP, 0.0));
        // m_backRight.getDriveController().setI(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveI, 0.0));
        // m_backRight.getDriveController().setD(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveD, 0.0));
        // m_backRight.getDriveController().setFF(SmartDashboard.getNumber(DashboardMap.kDrivetrainDriveFF, 0.0));
        // m_backRight.getTurnController().setP(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnP, 0.0));
        // m_backRight.getTurnController().setI(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnI, 0.0));
        // m_backRight.getTurnController().setD(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnD, 0.0));
        // m_backRight.getTurnController().setFF(SmartDashboard.getNumber(DashboardMap.kDrivetrainTurnFF, 0.0));
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

// I'll be back in a little bit
// gabagoo