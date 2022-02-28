package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Hood extends SubsystemBase implements AutoCloseable {
    public static final int kMiddle = 994;
    public static final double kMin = 0.02;
    public static final double kMax = 0.9;

    private final PWM m_left;
    private final PWM m_right;

    private final DigitalInput m_dutyCycleInput;
    private final DutyCycle m_dutyCycle;

    public Hood() {
        m_left = new PWM(RobotMap.kHoodServoLeft);
        m_right = new PWM(RobotMap.kHoodServoRight);

        m_dutyCycleInput = new DigitalInput(RobotMap.kHoodPulseWidthDigIO);
        m_dutyCycle = new DutyCycle(m_dutyCycleInput);

        m_left.setPeriodMultiplier(PWM.PeriodMultiplier.k4X);
        m_right.setPeriodMultiplier(PWM.PeriodMultiplier.k4X);

        setSpeed(0.0);
    }

    /** Sets the speed of the Hood 
     * @param speed a value between -1.0 and 1.0
    */
    public void setSpeed(double speed) {
        speed = speed * 100;
        int rawSpeed = (int) speed;
        m_left.setRaw(kMiddle + rawSpeed);
        m_right.setRaw(kMiddle - rawSpeed);
    }

    // returns 0.0 to 1.0
    public double getPosition() {
        return m_dutyCycle.getOutput();
    }

    @Override
    public void close() throws Exception{
        m_left.close();
        m_right.close();
        m_dutyCycleInput.close();
        m_dutyCycle.close();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Hood");
        builder.addDoubleProperty("Position", () -> getPosition(), null);
    }
}
