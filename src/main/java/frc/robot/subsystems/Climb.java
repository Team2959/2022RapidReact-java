package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climb extends SubsystemBase {

    private Solenoid climb;

    public Climb() {
        this.climb = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.kClimbSolenoid);
    }

    public void extendClimbHooks() {
        this.climb.set(true);
    }

    public void retractClimbHooks() {
        this.climb.set(false);
    }
}
