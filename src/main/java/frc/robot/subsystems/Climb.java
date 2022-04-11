package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardMap;
import frc.robot.RobotMap;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;


public class Climb extends SubsystemBase {

    private final CANSparkMax m_rightMotor;
    private final CANSparkMax m_leftMotor;
    private final CANSparkMax m_rightRotatorMotor;
    private final CANSparkMax m_leftRotatorMotor;
    private final SparkMaxPIDController m_rightController;
    private final SparkMaxPIDController m_leftController;
    private final SparkMaxPIDController m_rightRotatorController;
    private final SparkMaxPIDController m_leftRotatorController;
    private RelativeEncoder m_rightEncoder;
    private RelativeEncoder m_leftEncoder;
    private RelativeEncoder m_rightRotatorEncoder;
    private RelativeEncoder m_leftRotatorEncoder;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public static double kExtendPosition = 70, kRetractPosition = -25; // POSITION IS IN MOTOR ROTATIONS
    public static double kExtendRotatorPosition = 30, kRetractRotatorPosition = 0;

    public Climb() {
        m_rightMotor = new CANSparkMax(RobotMap.kRightClimbCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_leftMotor = new CANSparkMax(RobotMap.kLeftClimbCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_rightRotatorMotor = new CANSparkMax(RobotMap.kRightClimbRotaterCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_leftRotatorMotor = new CANSparkMax(RobotMap.kLeftClimbRotaterCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_rightController = m_rightMotor.getPIDController();
        m_leftController  = m_leftMotor.getPIDController();
        m_rightRotatorController = m_rightRotatorMotor.getPIDController();
        m_leftRotatorController = m_leftRotatorMotor.getPIDController();
        m_rightMotor.restoreFactoryDefaults();
        m_leftMotor.restoreFactoryDefaults();
        m_rightRotatorMotor.restoreFactoryDefaults();
        m_leftRotatorMotor.restoreFactoryDefaults();
        m_rightEncoder = m_rightMotor.getEncoder();
        m_leftEncoder = m_leftMotor.getEncoder();
        m_rightRotatorEncoder = m_rightMotor.getEncoder();
        m_leftRotatorEncoder = m_leftMotor.getEncoder();

        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        m_leftRotatorEncoder.setPosition(0);
        m_rightRotatorEncoder.setPosition(0);

        m_leftMotor.setInverted(true);

        m_leftMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_rightMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_rightRotatorMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_leftRotatorMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_leftMotor.setSmartCurrentLimit(30);
        m_rightMotor.setSmartCurrentLimit(30);

        kP = 5e-5;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.000156;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 4500;
        maxVel = 4500;        
        maxAcc = 4500;

        m_rightController.setI(kI);
        m_rightController.setD(kD);
        m_rightController.setP(kP);
        m_rightController.setIZone(kIz);
        m_rightController.setFF(kFF);
        m_rightController.setOutputRange(kMinOutput, kMaxOutput);
        
        m_leftController.setI(kI);
        m_leftController.setD(kD);
        m_leftController.setP(kP);
        m_leftController.setIZone(kIz);
        m_leftController.setFF(kFF);
        m_leftController.setOutputRange(kMinOutput, kMaxOutput);

        m_rightRotatorController.setI(kI);
        m_rightRotatorController.setD(kD);
        m_rightRotatorController.setP(kP);
        m_rightRotatorController.setIZone(kIz);
        m_rightRotatorController.setFF(kFF);
        m_rightRotatorController.setOutputRange(kMinOutput, kMaxOutput);
        
        m_leftRotatorController.setI(kI);
        m_leftRotatorController.setD(kD);
        m_leftRotatorController.setP(kP);
        m_leftRotatorController.setIZone(kIz);
        m_leftRotatorController.setFF(kFF);
        m_leftRotatorController.setOutputRange(kMinOutput, kMaxOutput);
        

        int smartMotionSlot = 0;
        m_rightController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_rightController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_rightController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_rightController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
        
        m_leftController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_leftController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_leftController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_leftController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        m_rightRotatorController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_rightRotatorController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_rightRotatorController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_rightRotatorController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
        
        m_leftRotatorController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_leftRotatorController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_leftRotatorController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_leftRotatorController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        // SmartDashboard.putNumber("P Gain", kP);
        // SmartDashboard.putNumber("I Gain", kI);
        // SmartDashboard.putNumber("D Gain", kD);
        // SmartDashboard.putNumber("I Zone", kIz);
        // SmartDashboard.putNumber("Feed Forward", kFF);
        // SmartDashboard.putNumber("Max Output", kMaxOutput);
        // SmartDashboard.putNumber("Min Output", kMinOutput);
    }

    public void extendClimbHooks() {
        m_rightController.setReference(SmartDashboard.getNumber(DashboardMap.kClimbExtendPosition, kExtendPosition), CANSparkMax.ControlType.kSmartMotion);
        m_leftController.setReference(SmartDashboard.getNumber(DashboardMap.kClimbExtendPosition, kExtendPosition), CANSparkMax.ControlType.kSmartMotion);
    }

    public void retractClimbHooks() {
        m_rightController.setReference(SmartDashboard.getNumber(DashboardMap.kClimbRetractPosition, kRetractPosition), CANSparkMax.ControlType.kSmartMotion);
        m_leftController.setReference(SmartDashboard.getNumber(DashboardMap.kClimbRetractPosition, kRetractPosition), CANSparkMax.ControlType.kSmartMotion);
    }

    public void rotateClimbHooksBack() {
        m_rightRotatorController.setReference(SmartDashboard.getNumber(DashboardMap.kClimbExtendRotatorPosition, kExtendRotatorPosition), CANSparkMax.ControlType.kSmartMotion);
        m_leftRotatorController.setReference(SmartDashboard.getNumber(DashboardMap.kClimbExtendRotatorPosition, kExtendRotatorPosition), CANSparkMax.ControlType.kSmartMotion);
    }

    public void rotateClimbHooksForward() {
        m_rightRotatorController.setReference(SmartDashboard.getNumber(DashboardMap.kClimbRetractRotatorPosition, kRetractRotatorPosition), CANSparkMax.ControlType.kSmartMotion);
        m_leftRotatorController.setReference(SmartDashboard.getNumber(DashboardMap.kClimbRetractRotatorPosition, kRetractRotatorPosition), CANSparkMax.ControlType.kSmartMotion);
    }

    public void keepCurrentPosition() {
        m_rightController.setReference(m_rightEncoder.getPosition(),  CANSparkMax.ControlType.kSmartMotion);
        m_leftController.setReference(m_leftEncoder.getPosition(),  CANSparkMax.ControlType.kSmartMotion);
    }

    public void keepRotatorsCurrentPosition() {
        m_rightRotatorController.setReference(m_rightRotatorEncoder.getPosition(),  CANSparkMax.ControlType.kSmartMotion);
        m_leftRotatorController.setReference(m_leftRotatorEncoder.getPosition(),  CANSparkMax.ControlType.kSmartMotion);
    }
}
