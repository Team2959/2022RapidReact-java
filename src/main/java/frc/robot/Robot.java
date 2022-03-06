// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import cwtech.trigger.ModeTrigger;

// Do code sa java ateema, do chowbaso

public class Robot extends TimedRobot {
    private RobotContainer m_robotContainer;
    private int m_initTicks = 1;

    @Override
    public void robotInit() {
        m_robotContainer = new RobotContainer();

        SmartDashboard.putNumber("Accelarator Speed", 0.85);
        SmartDashboard.putNumber("Shooter Speed", 3000);

        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveP, 0.05);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveI, 0.0);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveD, 0.001);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveFF, 0.02);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnP, 0.4);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnI, 0.00001);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnD, 0.0);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnFF, 0.0);
        // SmartDashboard.putNumber(DashboardMap.kOIXDeadband, 0.15);
        // SmartDashboard.putNumber(DashboardMap.kOIXExponent, 0.8);
        // SmartDashboard.putNumber(DashboardMap.kOIYDeadband, 0.15);
        // SmartDashboard.putNumber(DashboardMap.kOIYExponent, 0.8);
        // SmartDashboard.putNumber(DashboardMap.kOITurnDeadband, 0.25);
        // SmartDashboard.putNumber(DashboardMap.kOITurnExponent, 0.8);
        SmartDashboard.putNumber(DashboardMap.kIntakeSpeed, 0.4);
        // SmartDashboard.putNumber("Trajectory/TY Offset", 5.25);
        // SmartDashboard.putBoolean(DashboardMap.kHoodUseManualAngle, false);
        // SmartDashboard.putNumber(DashboardMap.kHoodManualAngle, 0.5);
        SmartDashboard.putNumber(DashboardMap.kTurretManualAnagle, 0.0);
    }

    @Override
    public void robotPeriodic() {
        if (m_initTicks > 0)
        {
            m_initTicks++;
            if (m_initTicks == 500)
            {
                m_initTicks = -1;
                m_robotContainer.hood.initialization();
                m_robotContainer.drivetrain.setInitalPositions();
                m_robotContainer.drivetrain.resetNavX();
                m_robotContainer.turret.resetEncoder();
            }
        }

        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        // ModeTrigger.registerMode(ModeTrigger.Mode.Disabled);
        m_robotContainer.drivetrain.onDisabledInit();
        m_robotContainer.oi.onDisabledInit();
        m_robotContainer.intake.onDisabledInit();
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        // ModeTrigger.registerMode(ModeTrigger.Mode.Autonomous);
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // ModeTrigger.registerMode(ModeTrigger.Mode.Teleop);
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() { 
    }
}
