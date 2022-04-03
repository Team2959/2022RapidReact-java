package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Turret extends SubsystemBase {
    
    private final CANSparkMax m_turretMotor;
    private final SparkMaxAlternateEncoder m_turretEncoder;
    private final SparkMaxPIDController m_turretController;

    static public final double kMaxDegreesForwards = 130;
    static public final double kMaxDegreesBackwards = -130;

    static public final double kTurretP = 0.04;
    static public final double kTurretI = 0.00001;
    static public final double kTurretD = 0.0;
    static public final double kTurretFF = 0.01;
    static public final double kTurretIZone = 1.0;
    
    public Turret() {
        m_turretMotor = new CANSparkMax(RobotMap.kTurretCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_turretEncoder = (SparkMaxAlternateEncoder) m_turretMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 4096);
        m_turretController = m_turretMotor.getPIDController();

        m_turretController.setFeedbackDevice(m_turretEncoder);
        m_turretController.setP(kTurretP);
        m_turretController.setI(kTurretI);
        m_turretController.setD(kTurretD);
        m_turretController.setIZone(kTurretIZone);
        m_turretController.setOutputRange(-0.8, 0.8);
        m_turretController.setFF(kTurretFF);
    
        m_turretController.setSmartMotionMaxVelocity(3000, 0);
        m_turretController.setSmartMotionMaxAccel(2000, 0);

        m_turretEncoder.setPositionConversionFactor(360);
    }

    public void onDisabledPeriodic() {
        // m_turretController.setP(SmartDashboard.getNumber(DashboardMap.kTurretP, kTurretP));
        // m_turretController.setI(SmartDashboard.getNumber(DashboardMap.kTurretI, kTurretI));
        // m_turretController.setD(SmartDashboard.getNumber(DashboardMap.kTurretD, kTurretD));
        // m_turretController.setFF(SmartDashboard.getNumber(DashboardMap.kTurretFF, kTurretFF));
        // m_turretController.setIZone(SmartDashboard.getNumber(DashboardMap.kTurretIZon, kTurretIZone));
    }

    public void resetEncoder() {
        m_turretEncoder.setPosition(0);
    }

    public void setDesiredAngle(double degrees) {
        double newTarget = Math.min(kMaxDegreesForwards ,Math.max(degrees, kMaxDegreesBackwards));
        REVLibError error = m_turretController.setReference(newTarget, CANSparkMax.ControlType.kPosition);
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
        double newTarget = Math.min(kMaxDegreesForwards ,Math.max(targetAngle, kMaxDegreesBackwards));
        setSpeed(newTarget > getAngleDegrees() ? 0.80 : -0.80);
    }

    private final double kVisionError = 2;
    public boolean isCloseEnoughToTarget(double targetAngle){
      return Math.abs(getAngleDegrees() - targetAngle) < kVisionError;
      }

    // @Override
    // public void initSendable(SendableBuilder builder) {
    //     builder.setSmartDashboardType("Turret");
    //     builder.addDoubleProperty("Encoder", () -> getAngleDegrees(), null);
    // }

}
