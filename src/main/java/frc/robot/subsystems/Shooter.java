
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {
    private final CANSparkMax m_mainMotor;
    private final CANSparkMax m_followerMotor;
    private final SparkMaxPIDController m_mainMotorController;
    private final SparkMaxRelativeEncoder m_mainMotorEncoder;
    private final Solenoid m_feeder;
    private final VictorSPX m_accelarator;
    // TODO implement accelarator Wheels

    public Shooter() {
        m_mainMotor = new CANSparkMax(RobotMap.kShooterPrimaryCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_followerMotor = new CANSparkMax(RobotMap.kShooterFollowerCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_mainMotorController = m_mainMotor.getPIDController();
        m_mainMotorEncoder = (SparkMaxRelativeEncoder) m_mainMotor.getEncoder();
        m_feeder = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.kFeederSolenoid);
        
        m_accelarator = new VictorSPX(RobotMap.kAccelaratorVictorSPX);
 
        m_mainMotorController.setFF(0.0008);
        m_mainMotorController.setP(0.0008);
        m_mainMotorController.setD(0);
    
        m_followerMotor.follow(m_mainMotor, true);
    }

    /** @param speed Value between 0 and 4500 */
    public void setVelocity(double rpm) {
        rpm = Math.min(4500, rpm);
        m_mainMotorController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }

    public double getVelocity() {
        return m_mainMotorEncoder.getVelocity();
    }

    public void setFeeder(boolean extended) {
        m_feeder.set(extended);
    }

    public boolean getFeeder() {
        return m_feeder.get();
    }

    /** @param speed A value between -1.0 and 1.0 */
    public void setAccelarator(double speed) {
        m_accelarator.set(VictorSPXControlMode.PercentOutput, speed);
    }
}

