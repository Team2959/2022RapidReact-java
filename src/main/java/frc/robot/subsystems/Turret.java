package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;

// import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Turret extends SubsystemBase {
    
    private final CANSparkMax m_turretMotor;
    private final SparkMaxAlternateEncoder m_turretEncoder;
    private final SparkMaxPIDController m_turretController;

    static public final double kMaxDegreesForwards = 130;
    static public final double kMaxDegreesBackwards = -130;
    
    public Turret() {
        m_turretMotor = new CANSparkMax(RobotMap.kTurretCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_turretEncoder = (SparkMaxAlternateEncoder) m_turretMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 4096);
        m_turretController = m_turretMotor.getPIDController();

        m_turretController.setFeedbackDevice(m_turretEncoder);
        // m_turretController.setP(0.04);
        // m_turretController.setI(0.00001);
        // m_turretController.setD(0.0);
        // m_turretController.setIZone(1.0);
        m_turretController.setOutputRange(-0.5, 0.5);
        m_turretController.setFF(0.01);
    
        m_turretController.setSmartMotionMaxVelocity(2000, 0);
        m_turretController.setSmartMotionMaxAccel(1500, 0);

        m_turretEncoder.setPositionConversionFactor(360);
    }

    public void resetEncoder() {
        m_turretEncoder.setPosition(0);
    }

    public void setDesiredAngle(double degrees) {
        REVLibError error = m_turretController.setReference(degrees, CANSparkMax.ControlType.kPosition);
        if(error != REVLibError.kOk) {
            System.err.println("Returned error on setDesiredAngle");
        }
    }

    public void setSpeed(double speed) {
        m_turretMotor.set(speed);
        //m_turretController.setReference(speed, CANSparkMax.ControlType.kSmartMotion);
    }

    public double getAngleDegrees() {
        return m_turretEncoder.getPosition();
    }

    public void setSpeedToTargetAngle(double targetAngle){
        setSpeed(targetAngle > getAngleDegrees() ? 0.80 : -0.80);
    }

    private final double kVisionError = 2;
    public boolean isCloseEnoughToTarget(double targetAngle){
        // double current = getAngleDegrees();
        // if (targetAngle < 0 && current < targetAngle)
        //   return true;
        //   if (targetAngle > 0 && current > targetAngle)
        //   return true;
      return Math.abs(getAngleDegrees() - targetAngle) < kVisionError;
      }

    // @Override
    // public void initSendable(SendableBuilder builder) {
    //     builder.setSmartDashboardType("Turret");
    //     builder.addDoubleProperty("Encoder", () -> getAngleDegrees(), null);
    // }

}
