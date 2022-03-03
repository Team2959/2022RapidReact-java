// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import cwtech.trigger.ModeTrigger;

// Do code sa java ateema, do chowbaso

public class Robot extends TimedRobot {
    private RobotContainer m_robotContainer;

    @Override
    public void robotInit() {
        m_robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().cancelAll();
        ModeTrigger.registerMode(ModeTrigger.Mode.Disabled);
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        CommandScheduler.getInstance().cancelAll();
        ModeTrigger.registerMode(ModeTrigger.Mode.Autonomous);
    }

    @Override
    public void autonomousPeriodic() {
    }

    private boolean hasSetInitalPositions = false;

    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().cancelAll();
        ModeTrigger.registerMode(ModeTrigger.Mode.Teleop);
        m_robotContainer.drivetrain.resetNavX();
        if(!hasSetInitalPositions) {
            m_robotContainer.drivetrain.setInitalPositions();
            hasSetInitalPositions = true;
        }
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        /*SmartDashboard.putBoolean("Test/Run", false);
        SmartDashboard.putNumber("Test/Speed", 0.0);
        SmartDashboard.putBoolean("Test/Hood Run", false);
        SmartDashboard.putNumber("Test/Hood Position", 0.0);
        ModeTrigger.registerMode(ModeTrigger.Mode.Test);
        CommandScheduler.getInstance().cancelAll();*/
    }

    //private final DashboardEntry runTest = new DashboardEntry("Test/Run Test", false);
    //private final DashboardEntry testVelocity = new DashboardEntry("Test/Velocity", 0);
    //private final DashboardEntry testVelocityFeedback = new DashboardEntry("Test/Feedback", 0);
    @Override
    public void testPeriodic() { 
        /*
        if(SmartDashboard.getBoolean("Test/Run", false)) {
            double i = SmartDashboard.getNumber("Test/Speed", 0.0);
            System.err.print("Running Motor: ");
            System.err.println(i);
            System.err.flush();
            m_robotContainer.shooter.setVelocity(i);
        }
        else if(SmartDashboard.getBoolean("Test/Hood Run", false)) {
            double i = SmartDashboard.getNumber("Test/Hood Position", 0.0);
            (new SetHoodAngleCommand(m_robotContainer, i)).schedule();
            SmartDashboard.putBoolean("Test/Hood Run", false);
        } 
        else {
            m_robotContainer.shooter.setVelocity(0);
        }
        */
    }
}
