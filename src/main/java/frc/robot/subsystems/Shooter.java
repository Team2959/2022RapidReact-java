
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardMap;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {
    public static final double kAcceleratorSpeed = 0.60;
    public static final double kShooterFf = 0.0013;
    public static final double kShooterKp = 0.000465;
    public static final double kShooterKi = 0.0;
    public static final double kShooterKd = 0.00007;
    public static final double kShooterEntryAngle = -70;
    public static final double kShooterMulti = 0.805; // 0.821;

    private final CANSparkMax m_mainMotor;
    private final CANSparkMax m_followerMotor;
    private final SparkMaxPIDController m_mainMotorController;
    private final SparkMaxRelativeEncoder m_mainMotorEncoder;
    private final VictorSPX m_accelerator;
    static public final double kWheelRadius = 2.5 * 0.0254;
    public static final double kIdleSpeed = 100;
    private double m_requestedVelocity = 0;

    public Shooter() {
        m_mainMotor = new CANSparkMax(RobotMap.kShooterPrimaryCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_followerMotor = new CANSparkMax(RobotMap.kShooterFollowerCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_mainMotorController = m_mainMotor.getPIDController();
        m_mainMotorEncoder = (SparkMaxRelativeEncoder) m_mainMotor.getEncoder();
        
        m_accelerator = new VictorSPX(RobotMap.kAcceleratorVictorSPX);
 
        m_mainMotorController.setFF(kShooterFf);
        m_mainMotorController.setP(kShooterKp);
        m_mainMotorController.setI(kShooterKi);
        m_mainMotorController.setD(kShooterKd);
    
        m_followerMotor.follow(m_mainMotor, true);
    }

    public void onDisabledPeriodic() {
        // m_mainMotorController.setFF(SmartDashboard.getNumber(DashboardMap.kShooterFf, kShooterFf));
        // m_mainMotorController.setP(SmartDashboard.getNumber(DashboardMap.kShooterP, kShooterKp));
        // m_mainMotorController.setI(SmartDashboard.getNumber(DashboardMap.kShooterI, kShooterKi));
        // m_mainMotorController.setD(SmartDashboard.getNumber(DashboardMap.kShooterD, kShooterKd));
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber(DashboardMap.kShooterVelocity, getVelocity());
    }

    /** @param speed Value between 0 and 4500 */
    public void setVelocity(double rpm) {
        rpm = Math.min(4500, rpm);
        m_requestedVelocity = rpm;
        m_mainMotorController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }

    public double getRequestedVelocity() {
        return m_requestedVelocity;
    }

    public double getVelocity() {
        return m_mainMotorEncoder.getVelocity();
    }

    /** @param speed A value between -1.0 and 1.0 */
    public void setAccelarator(double speed) {
        m_accelerator.set(VictorSPXControlMode.PercentOutput, speed);
    }

    public void setAccelaratorVelocity(double rpm) {
        rpm = Math.max(rpm, 11000);
        m_accelerator.set(VictorSPXControlMode.Velocity, rpm);
    }
}

