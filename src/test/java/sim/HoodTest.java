package sim;

import org.junit.*;

import common.Common;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.simulation.PWMSim;
import frc.robot.RobotMap;
import frc.robot.subsystems.Hood;

import static org.junit.Assert.*;

public class HoodTest {
    Hood hood;
    PWMSim left, right;

    @Before
    public void setup() {
        assert HAL.initialize(500, 0);
        hood = new Hood();
        left = new PWMSim(RobotMap.kHoodServoLeft);
        right = new PWMSim(RobotMap.kHoodServoRight);
    }

    @After
    public void shutdown() throws Exception {
        hood.close();
    }

    @Test
    public void resting() {
        hood.setSpeed(0.0);
        assertEquals(Hood.kMiddle, left.getRawValue(), Common.kDelta);
        assertEquals(Hood.kMiddle, right.getRawValue(), Common.kDelta);
    }

    @Test
    public void halfSpeed() {
        hood.setSpeed(0.5);
        assertEquals(Hood.kMiddle + 50, left.getRawValue(), Common.kDelta);
        assertEquals(Hood.kMiddle - 50, right.getRawValue(), Common.kDelta);
    }

    @Test 
    public void fullSpeed() {
        hood.setSpeed(1.0);
        assertEquals(Hood.kMiddle + 100, left.getRawValue(), Common.kDelta);
        assertEquals(Hood.kMiddle - 100, right.getRawValue(), Common.kDelta);
    }

    @Test 
    public void fullSpeedReverse() {
        hood.setSpeed(-1.0);
        assertEquals(Hood.kMiddle - 100, left.getRawValue(), Common.kDelta);
        assertEquals(Hood.kMiddle + 100, right.getRawValue(), Common.kDelta);
    }


}
