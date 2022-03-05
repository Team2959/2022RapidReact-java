package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Hood extends SubsystemBase implements AutoCloseable {
    public static final int kMiddle = 994;
    public static final double kMinEncoderOffset = 0.05;
    public static final double kMaxEncoderOffset = 0.79;
    public static final double kMaxEncoder = 0.98;
    public static final double kMinDegrees = 32;
    public static final double kMaxDegress = 58;
    private double m_EncoderPerDegreeSlope = 1;
    private double m_EncoderPerDegreeOffset = 0;
    private double m_minEncoder = 0;
    private double m_maxEncoder = 0;

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

    public void initialization()
    {
        double currentPosition = getPosition();
        m_minEncoder = currentPosition + kMinEncoderOffset;
        m_maxEncoder = currentPosition + kMaxEncoderOffset;
        // SmartDashboard.putNumber("Hood Start Position", currentPosition);
        // SmartDashboard.putNumber("Hood Min Encoder", m_minEncoder);
        // SmartDashboard.putNumber("Hood Max Encoder", m_maxEncoder);

        m_EncoderPerDegreeSlope = (m_maxEncoder - m_minEncoder) / (kMaxDegress - kMinDegrees);
        m_EncoderPerDegreeOffset = m_minEncoder - kMinDegrees * m_EncoderPerDegreeSlope;
    }

    public double getMaxEncoder() {
        return m_maxEncoder;
    }

    public double getMinEncoder() {
        return m_minEncoder;
    }

    public double convertToEncoderPositionFromDegrees(double degrees) {
        return degrees * m_EncoderPerDegreeSlope + m_EncoderPerDegreeOffset;
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
    public void periodic() {
        SmartDashboard.putNumber("Hood/Position", getPosition());
    }

    @Override
    public void close() throws Exception{
        m_left.close();
        m_right.close();
        m_dutyCycleInput.close();
        m_dutyCycle.close();
    }
}
