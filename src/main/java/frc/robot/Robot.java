// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Intake;
// import cwtech.trigger.ModeTrigger;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

// Do code sa java ateema, do chowbaso

public class Robot extends TimedRobot {
    private RobotContainer m_robotContainer = new RobotContainer();
    private int m_initTicks = 1;

    @Override
    public void robotInit() {
        LiveWindow.disableAllTelemetry();
        SmartDashboard.putString("MESSAGE", "started");
        // SmartDashboard.putString("Snap Turret MESSAGE", "");
        // SmartDashboard.putString("Hood Angle MESSAGE", "");
        // SmartDashboard.putString("Shooter Speed MESSAGE", "");
        SmartDashboard.putData("CS", CommandScheduler.getInstance());

        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveP, SwerveModule.kDriveKp);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveI, SwerveModule.kDriveKi);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveD, SwerveModule.kDriveKd);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveFF, SwerveModule.kDriveFf);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnP, SwerveModule.kTurnKp);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnI, SwerveModule.kTurnKi);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnD, SwerveModule.kTurnKd);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnFF, SwerveModule.kTurnFf);
        // SmartDashboard.putNumber(DashboardMap.kOIXDeadband, OI.kDriverXdeadband);
        // SmartDashboard.putNumber(DashboardMap.kOIXExponent, OI.kDriverXexponent);
        // SmartDashboard.putNumber(DashboardMap.kOIYDeadband, OI.kDriverYdeadband);
        // SmartDashboard.putNumber(DashboardMap.kOIYExponent, OI.kDriverYexponent);
        // SmartDashboard.putNumber(DashboardMap.kOITurnDeadband, OI.kDriverRotationDeadband);
        // SmartDashboard.putNumber(DashboardMap.kOITurnExponent, OI.kDriverRotationExponent);

        SmartDashboard.putNumber(DashboardMap.kIntakeSpeed, Intake.kIntakeSpeed);

        SmartDashboard.putNumber(DashboardMap.kTrajectoryTyOffset, Vision.kCameraTYOffset);

        // SmartDashboard.putBoolean(DashboardMap.kHoodUseManualAngle, false);
        // SmartDashboard.putNumber(DashboardMap.kHoodManualAngle, 0.5);

        // SmartDashboard.putBoolean(DashboardMap.kTurretUseManualAngle, false);
        // SmartDashboard.putNumber(DashboardMap.kTurretManualAngle, 0.0);
        // SmartDashboard.putNumber(DashboardMap.kTurretP, Turret.kTurretP);
        // SmartDashboard.putNumber(DashboardMap.kTurretI, Turret.kTurretI);
        // SmartDashboard.putNumber(DashboardMap.kTurretD, Turret.kTurretD);
        // SmartDashboard.putNumber(DashboardMap.kTurretFF, Turret.kTurretFF);
        // SmartDashboard.putNumber(DashboardMap.kTurretIZon, Turret.kTurretIZone);

        // SmartDashboard.putBoolean(DashboardMap.kShooterUseManualSpeed, false);
        // SmartDashboard.putNumber(DashboardMap.kShooterManualSpeed, 1500);
        // SmartDashboard.putNumber(DashboardMap.kShooterAcceleratorSpeed, Shooter.kAcceleratorSpeed);
        // SmartDashboard.putNumber(DashboardMap.kShooterFf, Shooter.kFrontShooterFF);
        // SmartDashboard.putNumber(DashboardMap.kShooterP, Shooter.kFrontShooterP);
        // SmartDashboard.putNumber(DashboardMap.kShooterI, Shooter.kFrontShooterI);
        // SmartDashboard.putNumber(DashboardMap.kShooterD, Shooter.kFrontShooterD);
        // SmartDashboard.putNumber(DashboardMap.kShooterMulti, Shooter.kShooterMulti);
        SmartDashboard.putNumber(DashboardMap.kShooterEntryAngle, Shooter.kShooterEntryAngle);
        SmartDashboard.putNumber(DashboardMap.kShooterMulti, 1.0);
        SmartDashboard.putNumber(DashboardMap.kBackShooterMulti, 1.0);
        SmartDashboard.putNumber(DashboardMap.kShooterAcceleratorSpeed, Shooter.kAcceleratorSpeed);
        SmartDashboard.putNumber("Drive Reducer", 1.00);

        SmartDashboard.putNumber(DashboardMap.kShooterFf, Shooter.kFrontShooterFF);
        SmartDashboard.putNumber(DashboardMap.kShooterP,  Shooter.kFrontShooterP);
        SmartDashboard.putNumber(DashboardMap.kShooterI,  Shooter.kFrontShooterI);
        SmartDashboard.putNumber(DashboardMap.kShooterD,  Shooter.kFrontShooterD);

        SmartDashboard.putNumber("Shooter/Back/FF",  Shooter.kBackShooterFF);
        SmartDashboard.putNumber ("Shooter/Back/P",   Shooter.kBackShooterP);
        SmartDashboard.putNumber ("Shooter/Back/I",   Shooter.kBackShooterI);
        SmartDashboard.putNumber ("Shooter/Back/D",   Shooter.kBackShooterD);

        SmartDashboard.putBoolean(DashboardMap.kShooterDoManualBack, false);
        SmartDashboard.putBoolean(DashboardMap.kShooterDoManualFront, false);
        SmartDashboard.putNumber(DashboardMap.kShooterManualFront, 0.0);
        SmartDashboard.putNumber(DashboardMap.kShooterManualBack, 0.0);

        

        SmartDashboard.putBoolean(DashboardMap.kFieldCentric, true);
        m_robotContainer.init();
    }

    @Override
    public void robotPeriodic() {
        if (m_initTicks > 0)
        {
            m_initTicks++;
            if (m_initTicks == 100)
            {
                m_initTicks = -1;
                m_robotContainer.drivetrain.setInitalPositions();
                m_robotContainer.drivetrain.resetNavX();
                m_robotContainer.turret.resetEncoder();
                SmartDashboard.putString("MESSAGE", "initialized");
            }
        }

        CommandScheduler.getInstance().run();
        m_robotContainer.periodic();
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
        m_robotContainer.shooter.onDisabledPeriodic();
        m_robotContainer.turret.onDisabledPeriodic();
    }

    @Override
    public void autonomousInit() {
        m_robotContainer.m_autoChooser.getSelected().schedule();
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
