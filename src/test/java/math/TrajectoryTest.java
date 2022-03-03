// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package math;

import static org.junit.Assert.*;

import org.junit.*;

import cwtech.util.BasicTrajectory;
import cwtech.util.BasicTrajectory.TrajectoryCalculation;

/** Tests to test the Trajectory calculation */
public class TrajectoryTest {
    public static final double kDelta = 1e-2;

    private void basicCheck(double S, double d, double H, double expectedA, double expectedV) {
        TrajectoryCalculation calculation = BasicTrajectory.calculate(S, d, H);
        double a = calculation.m_shootingAngleDegrees;
        double v = calculation.m_exitVelocityMetersPerSecond;
        assertEquals(expectedA, a, kDelta);
        assertEquals(expectedV, v, kDelta);
    }

    @Test
    public void checkCalculate() {
        basicCheck(-68, 2.6, 2.64, 77.486, 8.817);
        basicCheck(-88, 100, 4.45, 88.006, 118.803);
        basicCheck(-25, 22.3, 7.2, 48.0367, 17.598);
    }

    @Test
    public void tanAndAtanCheck() {
        assertEquals(1, BasicTrajectory.tan(45), kDelta);
        assertEquals(BasicTrajectory.atan(1), 45, kDelta);
    }
}
