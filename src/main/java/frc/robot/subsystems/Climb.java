package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Climb extends SubsystemBase {

    private final CANSparkMax m_rightMotor;
    private final CANSparkMax m_leftMotor;
    private final SparkMaxPIDController m_rightController;
    private final SparkMaxPIDController m_leftController;
    private RelativeEncoder m_rightEncoder;
    private RelativeEncoder m_leftEncoder;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public double kExtendPosition = 70, kContractPosition = 5; // POSITION IS IN MOTOR ROTATIONS


    // private Solenoid m_climb;

    public Climb() {
        m_rightMotor = new CANSparkMax(RobotMap.kRightClimbCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_leftMotor = new CANSparkMax(RobotMap.kLeftClimbCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_rightController = m_rightMotor.getPIDController();
        m_leftController =m_leftMotor.getPIDController();
        m_rightMotor.restoreFactoryDefaults();
        m_leftMotor.restoreFactoryDefaults();
        m_rightEncoder = m_rightMotor.getEncoder();
        m_leftEncoder = m_leftMotor.getEncoder();

        m_leftMotor.setInverted(true);

        // m_climb = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.kClimbSolenoid);

        kP = 5e-5;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.000156;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 4500;
        maxVel = 2000;
        maxAcc = 1500;

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

        int smartMotionSlot = 0;
        m_rightController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_rightController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_rightController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_rightController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
        
        m_leftController.setOutputRange(kMinOutput, kMaxOutput);
        m_leftController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_leftController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_leftController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_leftController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

        SmartDashboard.putBoolean("Mode", true);
    }

    public void extendClimbHooks() {
        // m_climb.set(true);

        m_rightController.setReference(kExtendPosition, CANSparkMax.ControlType.kSmartMotion);
        m_leftController.setReference(kExtendPosition, CANSparkMax.ControlType.kSmartMotion);

    }

    public void retractClimbHooks() {
        // m_climb.set(false);
        m_rightController.setReference(kContractPosition, CANSparkMax.ControlType.kSmartMotion);
        m_leftController.setReference(kContractPosition, CANSparkMax.ControlType.kSmartMotion);
    }

    public void keepCurrentPosition() {
        m_rightController.setReference(m_rightEncoder.getPosition(),  CANSparkMax.ControlType.kSmartMotion);
        m_leftController.setReference(m_leftEncoder.getPosition(),  CANSparkMax.ControlType.kSmartMotion);
    }
}
