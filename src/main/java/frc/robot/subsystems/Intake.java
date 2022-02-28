package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase implements AutoCloseable {
    private final Solenoid m_arms;
    private final CANSparkMax m_motor;

    private final double kIntakeSpeed = 0.50;

    public Intake() {
        m_arms = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.kIntakeArmsSolenoid);
        m_motor = new CANSparkMax(RobotMap.kIntakeCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
    }

    public void toggleIntake() {
        if (m_arms.get() == true) {
            m_arms.set(false);
            m_motor.set(0);
        } else {
            m_arms.set(true);
            m_motor.set(kIntakeSpeed);
        }
    }

    public void reverseIntake() {
        if (m_arms.get() == true) {
            m_motor.set(-kIntakeSpeed);
        }
    }

    public void restoreIntakeDirection() {
        if(m_arms.get() == true) {
            m_motor.set(kIntakeSpeed);
        }
    }

    @Override
    public void close() {
        m_arms.close();
        m_motor.close();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Intake");
        builder.addBooleanProperty("Arms", () -> m_arms.get(), (boolean value) -> m_arms.set(value));
        builder.addDoubleProperty("Motor Speed", () -> m_motor.get(), null);
    }
}
