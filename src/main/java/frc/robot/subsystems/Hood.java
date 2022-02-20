package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Hood extends SubsystemBase implements AutoCloseable {
    public static final int kMiddle = 994;
    public static final double kMin = 0.02;
    public static final double kMax = 0.9;

    private final PWM left;
    private final PWM right;

    private final DigitalInput dutyCycleInput;
    private final DutyCycle dutyCycle;

    public Hood() {
        this.left = new PWM(RobotMap.kHoodServoLeft);
        this.right = new PWM(RobotMap.kHoodServoRight);

        this.dutyCycleInput = new DigitalInput(RobotMap.kHoodPulseWidthDigIO);
        this.dutyCycle = new DutyCycle(this.dutyCycleInput);

        this.left.setPeriodMultiplier(PWM.PeriodMultiplier.k4X);
        this.right.setPeriodMultiplier(PWM.PeriodMultiplier.k4X);

        setSpeed(0.0);
    }

    /** Sets the speed of the Hood 
     * @param speed a value between -1.0 and 1.0
    */
    public void setSpeed(double speed) {
        speed = speed * 100;
        int rawSpeed = (int) speed;
        this.left.setRaw(kMiddle + rawSpeed);
        this.right.setRaw(kMiddle - rawSpeed);
    }

    // returns 0.0 to 1.0
    public double getPosition() {
        return this.dutyCycle.getOutput();
    }

    @Override
    public void close() throws Exception{
        this.left.close();
        this.right.close();
        this.dutyCycleInput.close();
        this.dutyCycle.close();
    }
}
