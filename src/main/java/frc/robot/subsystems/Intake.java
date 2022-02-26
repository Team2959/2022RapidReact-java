package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase implements AutoCloseable {
    private final Solenoid arms;
    private final CANSparkMax motor;

    private final double kIntakeSpeed = 0.50;

    public Intake() {
        this.arms = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.kIntakeArmsSolenoid);
        this.motor = new CANSparkMax(RobotMap.kIntakeCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
    }

    public void toggleIntake() {
        if (this.arms.get() == true) {
            this.arms.set(false);
            this.motor.set(0);
        } else {
            this.arms.set(true);
            this.motor.set(kIntakeSpeed);
        }
    }

    public void reverseIntake() {
        if (this.arms.get() == true) {
            this.motor.set(-kIntakeSpeed);
        }
    }

    public void restoreIntakeDirection() {
        if(this.arms.get() == true) {
            this.motor.set(kIntakeSpeed);
        }
    }

    @Override
    public void close() {
        this.arms.close();
        this.motor.close();
    }
}
