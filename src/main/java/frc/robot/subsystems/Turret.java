package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Turret extends SubsystemBase {
    
    private final CANSparkMax m_turretMotor;
    private final SparkMaxAlternateEncoder m_turretEncoder;
    private final SparkMaxPIDController m_turretController;

    static public final double kMaxDegreesForwards = 270;
    static public final double kMaxDegreesBackwards = -270;
    
    public Turret() {
        m_turretMotor = new CANSparkMax(RobotMap.kTurretCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_turretEncoder = (SparkMaxAlternateEncoder) m_turretMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 4096);
        m_turretController = m_turretMotor.getPIDController();

        m_turretController.setOutputRange(-0.5, 0.5);
        m_turretController.setFeedbackDevice(m_turretEncoder);
        m_turretController.setFF(0.1);
        m_turretEncoder.setPositionConversionFactor(360);
    
        m_turretController.setSmartMotionMaxVelocity(2000, 0);
        m_turretController.setSmartMotionMaxAccel(1500, 0);
    }

    public void setDesiredAngle(double degrees) {
        m_turretController.setReference(degrees, CANSparkMax.ControlType.kPosition);
    }

    public void setSpeed(double speed) {
        m_turretController.setReference(speed, CANSparkMax.ControlType.kSmartMotion);
    }

    public double getAngleDegrees() {
        return m_turretEncoder.getPosition();
    }

}
