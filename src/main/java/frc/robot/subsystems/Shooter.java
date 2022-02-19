
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {
    private final CANSparkMax main;
    private final CANSparkMax follower;
    private final SparkMaxPIDController controller;
    private final SparkMaxRelativeEncoder encoder;
    private final Solenoid kicker;
    // TODO implement accelarator Wheels

    public Shooter() {
        this.main = new CANSparkMax(RobotMap.kShooterPrimaryCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        this.follower = new CANSparkMax(RobotMap.kShooterFollowerCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        this.controller = this.main.getPIDController();
        this.encoder = (SparkMaxRelativeEncoder) this.main.getEncoder();
        this.kicker = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.kKickerSolenoid);
 
        this.controller.setFF(0.0002);
        this.controller.setP(0.0004);
        this.controller.setD(0);
    
        this.follower.follow(this.main, true);
    }

    public void setVelocity(double rpm) {
        this.controller.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }

    public double getVelocity() {
        return this.encoder.getVelocity();
    }

    public void setKicker(boolean extended) {
        this.kicker.set(extended);
    }

    public boolean getKicker() {
        return this.kicker.get();
    }
}

