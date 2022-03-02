package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;

public class SwerveModule implements Sendable {
    private CANSparkMax m_driveMotor;
    private CANSparkMax m_turnMotor;
    private DigitalInput m_dutyCycleInput;
    private DutyCycle m_dutyCycleEncoder;
    private SparkMaxRelativeEncoder m_driveEncoder;
    private SparkMaxAlternateEncoder m_turnEncoder;
    private SparkMaxPIDController m_drivePIDController;
    private SparkMaxPIDController m_turnPIDController;
    private final double m_turnOffset;
    private final String m_name;

    private static final double kWheelRadius = 2.0 * 0.254; // 2" * 0.254 m / inch
    private static final int kEncoderResolution = 4096;
    private static final double kGearboxRatio = 1.0 / 6.86; // One turn of the wheel is 6.86 turns of the motor
    private static final double kDrivePositionFactor = (2.0 * Math.PI * kWheelRadius * kGearboxRatio);

    // As found in: https://github.com/wpilibsuite/allwpilib/blob/main/wpilibjExamples/src/main/java/edu/wpi/first/wpilibj/examples/swervebot/SwerveModule.java
    private static final double kDrivePositionFactor2 = (2.0 * Math.PI * kWheelRadius / kEncoderResolution);
    private static final double kTurnPositionFactor2 = (2.0 * Math.PI / kEncoderResolution);

    public SwerveModule(int driveMotor, int turnMotor, int dutyCycle, double turnOffset, String name) {

        m_driveMotor = new CANSparkMax(driveMotor, CANSparkMax.MotorType.kBrushless);
        m_turnMotor = new CANSparkMax(turnMotor, CANSparkMax.MotorType.kBrushless);
        m_driveMotor.restoreFactoryDefaults();

        m_name = name;

        m_turnOffset = turnOffset;

        m_dutyCycleInput = new DigitalInput(dutyCycle);
        m_dutyCycleEncoder = new DutyCycle(m_dutyCycleInput);

        m_driveEncoder = (SparkMaxRelativeEncoder) m_driveMotor.getEncoder();
        m_turnEncoder = (SparkMaxAlternateEncoder) m_turnMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, kEncoderResolution);
    
        m_drivePIDController = m_driveMotor.getPIDController();
        m_turnPIDController = m_turnMotor.getPIDController();

        m_driveEncoder.setPositionConversionFactor(kDrivePositionFactor2);
        m_driveEncoder.setVelocityConversionFactor(kDrivePositionFactor2 / 60.0);
        
        m_drivePIDController.setP(0.05);
        m_drivePIDController.setI(0.0);
        m_drivePIDController.setD(0.001);
        m_drivePIDController.setFF(0.02);
        m_drivePIDController.setIZone(600);

        m_turnPIDController.setFeedbackDevice(m_turnEncoder);
        m_turnPIDController.setP(0.4);
        m_turnPIDController.setI(0.00001);
        m_turnPIDController.setD(0.0);
        m_turnPIDController.setIZone(1.0);

        m_turnEncoder.setPositionConversionFactor(kTurnPositionFactor2);
    }

    public void periodic() {
        SmartDashboard.putNumber(m_name + "/Encoder", getAbsoluteEncoderPosition());
    }

    public double getAbsoluteEncoderPosition() {
        double initalPosition = m_dutyCycleEncoder.getOutput();
        double initalPositionInRadians = initalPosition * 2.0 * Math.PI;
        double initalPositionInRadiansScaled = new Rotation2d(initalPositionInRadians - m_turnOffset).getRadians();
        return initalPositionInRadiansScaled;
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(m_driveEncoder.getVelocity(), new Rotation2d(m_turnEncoder.getPosition()));
    }

    public void setDesiredState(SwerveModuleState referenceState) {
        SwerveModuleState state = SwerveModuleState.optimize(referenceState, new Rotation2d(m_turnEncoder.getPosition()));
        
        SmartDashboard.putNumber(m_name + "/Drive Speed", state.speedMetersPerSecond);
        SmartDashboard.putNumber(m_name + "/Drive Reference", state.speedMetersPerSecond / kDrivePositionFactor);
        m_drivePIDController.setReference(state.speedMetersPerSecond / kDrivePositionFactor, CANSparkMax.ControlType.kVelocity);
        
        Rotation2d delta = state.angle.minus(new Rotation2d(m_turnEncoder.getPosition()));
        double setpoint = m_turnEncoder.getPosition() + delta.getRadians();

        m_turnPIDController.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    public void setInitalPosition() {
        m_turnEncoder.setPosition(getAbsoluteEncoderPosition());
    }

    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("SwerveModule");
        builder.addDoubleProperty("absoluteAngle", this::getAbsoluteEncoderPosition, null);
        builder.addDoubleProperty("relativeAngle", () -> m_turnEncoder.getPosition(), null);
        builder.addDoubleProperty("driveVelocity", () -> m_driveEncoder.getVelocity(), null);
        builder.addDoubleProperty("turnVelocity", () -> m_turnEncoder.getVelocity(), null);
        builder.addDoubleProperty("driveP", () -> m_drivePIDController.getP(), (double d) -> m_drivePIDController.setP(d));
        builder.addDoubleProperty("driveI", () -> m_drivePIDController.getI(), (double d) -> m_drivePIDController.setI(d));
        builder.addDoubleProperty("driveD", () -> m_drivePIDController.getD(), (double d) -> m_drivePIDController.setD(d));
        builder.addDoubleProperty("driveFF", () -> m_drivePIDController.getFF(), (double d) -> m_drivePIDController.setFF(d));
        builder.addDoubleProperty("turnP", () -> m_turnPIDController.getP(), (double d) -> m_turnPIDController.setP(d));
        builder.addDoubleProperty("turnI", () -> m_turnPIDController.getI(), (double d) -> m_turnPIDController.setI(d));
        builder.addDoubleProperty("turnD", () -> m_turnPIDController.getD(), (double d) -> m_turnPIDController.setD(d));
        builder.addDoubleProperty("turnFF", () -> m_turnPIDController.getFF(), (double d) -> m_turnPIDController.setFF(d));
    }

    public SparkMaxPIDController getDriveController() {
        return m_drivePIDController;
    }
}


