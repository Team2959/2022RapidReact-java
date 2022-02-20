package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Turret extends SubsystemBase {
    
    private final CANSparkMax turretMotor;
    private final SparkMaxAlternateEncoder turretEncoder;
    private final SparkMaxPIDController turretController;

    static public final double kMaxDegreesForwards = 270;
    static public final double kMaxDegreesBackwards = -270;
    
    public Turret() {
        this.turretMotor = new CANSparkMax(RobotMap.kTurretCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        this.turretEncoder = (SparkMaxAlternateEncoder) this.turretMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 4096);
        this.turretController = this.turretMotor.getPIDController();

        this.turretController.setOutputRange(-0.5, 0.5);
        this.turretController.setFeedbackDevice(this.turretEncoder);
        this.turretController.setFF(0.1);
        this.turretEncoder.setPositionConversionFactor(360);
    
        this.turretController.setSmartMotionMaxVelocity(2000, 0);
        this.turretController.setSmartMotionMaxAccel(1500, 0);
    }

    public void setDesiredAngle(double degrees) {
        this.turretController.setReference(degrees, CANSparkMax.ControlType.kPosition);
    }

    public void setSpeed(double speed) {
        this.turretController.setReference(speed, CANSparkMax.ControlType.kSmartMotion);
    }

    public double getAngleDegrees() {
        return this.turretEncoder.getPosition();
    }

}
