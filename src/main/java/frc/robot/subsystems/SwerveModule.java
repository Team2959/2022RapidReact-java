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
    private CANSparkMax driveMotor;
    private CANSparkMax turnMotor;
    private DigitalInput dutyCycleInput;
    private DutyCycle dutyCycleEncoder;
    private SparkMaxRelativeEncoder driveEncoder;
    private SparkMaxAlternateEncoder turnEncoder;
    private SparkMaxPIDController drivePIDController;
    private SparkMaxPIDController turnPIDController;
    private final double turnOffset;
    private final String name;

    private static final double kWheelRadius = 2.0 * 0.254; // 2" * 0.254 m / inch
    private static final int kEncoderResolution = 4096;
    private static final double kGearboxRatio = 1.0 / 6.86; // One turn of the wheel is 6.86 turns of the motor
    private static final double kDrivePositionFactor = (2.0 * Math.PI * kWheelRadius * kGearboxRatio);

    public SwerveModule(int driveMotor, int turnMotor, int dutyCycle, double turnOffset, String name) {

        this.driveMotor = new CANSparkMax(driveMotor, CANSparkMax.MotorType.kBrushless);
        this.turnMotor = new CANSparkMax(turnMotor, CANSparkMax.MotorType.kBrushless);

        this.name = name;

        this.turnOffset = turnOffset;

        this.dutyCycleInput = new DigitalInput(dutyCycle);
        this.dutyCycleEncoder = new DutyCycle(this.dutyCycleInput);

        this.driveEncoder = (SparkMaxRelativeEncoder) this.driveMotor.getEncoder();
        this.turnEncoder = (SparkMaxAlternateEncoder) this.turnMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, kEncoderResolution);
    
        this.drivePIDController = this.driveMotor.getPIDController();
        this.turnPIDController = this.turnMotor.getPIDController();

        this.driveEncoder.setPositionConversionFactor(kDrivePositionFactor);
        this.driveEncoder.setVelocityConversionFactor(kDrivePositionFactor / 60);
        
        this.drivePIDController.setP(0.0003);
        this.drivePIDController.setI(0.0);
        this.drivePIDController.setD(0.0);
        this.drivePIDController.setFF(0.0002);
        this.drivePIDController.setIZone(600);

        this.turnPIDController.setFeedbackDevice(this.turnEncoder);
        this.turnPIDController.setP(0.4);
        this.turnPIDController.setI(0.00001);
        this.turnPIDController.setD(0.0);
        this.turnPIDController.setIZone(1.0);

        this.turnEncoder.setPositionConversionFactor(2.0 * Math.PI);
    }

    public void periodic() {
        SmartDashboard.putNumber(this.name + "/Encoder", getAbsoluteEncoderPosition());
    }

    public double getAbsoluteEncoderPosition() {
        double initalPosition = this.dutyCycleEncoder.getOutput();
        double initalPositionInRadians = initalPosition * 2.0 * Math.PI;
        double initalPositionInRadiansScaled = new Rotation2d(initalPositionInRadians - this.turnOffset).getRadians();
        return initalPositionInRadiansScaled;
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(this.driveEncoder.getVelocity(), new Rotation2d(this.turnEncoder.getPosition()));
    }

    public void setDesiredState(SwerveModuleState referenceState) {
        SwerveModuleState state = SwerveModuleState.optimize(referenceState, new Rotation2d(this.turnEncoder.getPosition()));
        
        this.drivePIDController.setReference(state.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity);
        
        double delta = state.angle.getRadians() - new Rotation2d(this.turnEncoder.getPosition()).getRadians();
        double setpoint = this.turnEncoder.getPosition() + delta;

        this.turnPIDController.setReference(setpoint, CANSparkMax.ControlType.kPosition);
    }

    public void setInitalPosition() {
        this.turnEncoder.setPosition(getAbsoluteEncoderPosition());
    }

    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("SwerveModule");
        builder.addDoubleProperty("absoluteAngle", this::getAbsoluteEncoderPosition, null);
    }
}


