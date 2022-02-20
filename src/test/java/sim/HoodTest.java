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
        this.hood = new Hood();
        this.left = new PWMSim(RobotMap.kHoodServoLeft);
        this.right = new PWMSim(RobotMap.kHoodServoRight);
    }

    @After
    public void shutdown() throws Exception {
        hood.close();
    }

    @Test
    public void resting() {
        this.hood.setSpeed(0.0);
        assertEquals(Hood.kMiddle, this.left.getRawValue(), Common.kDelta);
        assertEquals(Hood.kMiddle, this.right.getRawValue(), Common.kDelta);
    }

    @Test
    public void halfSpeed() {
        this.hood.setSpeed(0.5);
        assertEquals(Hood.kMiddle + 50, this.left.getRawValue(), Common.kDelta);
        assertEquals(Hood.kMiddle - 50, this.right.getRawValue(), Common.kDelta);
    }

    @Test 
    public void fullSpeed() {
        this.hood.setSpeed(1.0);
        assertEquals(Hood.kMiddle + 100, this.left.getRawValue(), Common.kDelta);
        assertEquals(Hood.kMiddle - 100, this.right.getRawValue(), Common.kDelta);
    }

    @Test 
    public void fullSpeedReverse() {
        this.hood.setSpeed(-1.0);
        assertEquals(Hood.kMiddle - 100, this.left.getRawValue(), Common.kDelta);
        assertEquals(Hood.kMiddle + 100, this.right.getRawValue(), Common.kDelta);
    }


}
