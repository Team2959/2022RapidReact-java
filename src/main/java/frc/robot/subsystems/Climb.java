package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climb extends SubsystemBase {

    private Solenoid m_climb;

    public Climb() {
        m_climb = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.kClimbSolenoid);
    }

    public void extendClimbHooks() {
        m_climb.set(true);
    }

    public void retractClimbHooks() {
        m_climb.set(false);
    }

    public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("solenoid", () -> m_climb.get(), null);
    }
}
