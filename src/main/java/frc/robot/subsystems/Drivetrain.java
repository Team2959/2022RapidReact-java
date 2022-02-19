package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {

    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;


    private static final Translation2d frontLeftLocation = new Translation2d(+0.381, -0.381);
    private static final Translation2d frontRightLocation = new Translation2d(-0.381, -0.381);
    private static final Translation2d backLeftLocation = new Translation2d(+0.381, +0.381);
    private static final Translation2d backRightLocation = new Translation2d(-0.381, +0.381);


    private SwerveModule frontLeft;
    private SwerveModule frontRight;
    private SwerveModule backLeft;
    private SwerveModule backRight;

    private AHRS navX;

    private SwerveDriveKinematics kinematics;
    private SwerveDriveOdometry odometry;

    public Drivetrain() {
        this.navX = new AHRS(Port.kMXP);

        this.frontLeft = new SwerveModule(RobotMap.kFrontLeftDriveCANSparkMaxMotor,
                RobotMap.kFrontLeftTurnCANSparkMaxMotor, RobotMap.kFrontLeftTurnPulseWidthDigIO,
                RobotMap.kZeroedFrontLeft);
        this.frontRight = new SwerveModule(RobotMap.kFrontRightDriveCANSparkMaxMotor,
                RobotMap.kFrontRightTurnCANSparkMaxMotor, RobotMap.kFrontRightTurnPulseWidthDigIO,
                RobotMap.kZeroedFrontRight);
        this.backLeft = new SwerveModule(RobotMap.kBackLeftDriveCANSparkMaxMotor,
                RobotMap.kBackLeftTurnCANSparkMaxMotor, RobotMap.kBackLeftTurnPulseWidthDigIO,
                RobotMap.kZeroedBackLeft);
        this.backRight = new SwerveModule(RobotMap.kBackRightDriveCANSparkMaxMotor,
                RobotMap.kBackRightTurnCANSparkMaxMotor, RobotMap.kBackRightTurnPulseWidthDigIO,
                RobotMap.kZeroedBackRight);

        this.kinematics = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation, backLeftLocation,
                backRightLocation);
        this.odometry = new SwerveDriveOdometry(this.kinematics, this.navX.getRotation2d());
    }

    public void drive(double xMetersPerSecond, double yMetersPerSecond, double rotationRadiansPerSecond,
            boolean fieldRelative) {
        SwerveModuleState[] states = this.kinematics.toSwerveModuleStates(fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xMetersPerSecond, yMetersPerSecond, rotationRadiansPerSecond,
                        this.navX.getRotation2d())
                : new ChassisSpeeds(xMetersPerSecond, yMetersPerSecond, rotationRadiansPerSecond));

        SwerveModuleState fl = states[0];
        SwerveModuleState fr = states[1];
        SwerveModuleState bl = states[2];
        SwerveModuleState br = states[3];

        this.frontLeft.setDesiredState(fl);
        this.frontRight.setDesiredState(fr);
        this.backLeft.setDesiredState(bl);
        this.backRight.setDesiredState(br);
    }

    //
    @Override
    public void periodic() {
        this.odometry.update(this.navX.getRotation2d(), frontLeft.getState(), frontRight.getState(),
                backLeft.getState(), backRight.getState());
    }

    public void setInitalPositions() {
        this.frontLeft.setInitalPosition();
        this.frontRight.setInitalPosition();
        this.backLeft.setInitalPosition();
        this.backRight.setInitalPosition();
    }

    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Drivetrain");
    }

}

// I'll be back in a little bit