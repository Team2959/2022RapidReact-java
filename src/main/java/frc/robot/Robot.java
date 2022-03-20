// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.DriveOnlyAutoCommand;
// import frc.robot.commands.SnapTurretToTarget;
import frc.robot.subsystems.Intake;
// import cwtech.trigger.ModeTrigger;
// import frc.robot.subsystems.SwerveModule;
// import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

// Do code sa java ateema, do chowbaso

public class Robot extends TimedRobot {
    private RobotContainer m_robotContainer = new RobotContainer();
    private AutoCommand m_autoCommand = new AutoCommand(m_robotContainer);
    private DriveOnlyAutoCommand m_autoCommandOnly = new DriveOnlyAutoCommand(m_robotContainer);
    private int m_initTicks = 1;

    @Override
    public void robotInit() {
        LiveWindow.disableAllTelemetry();
        SmartDashboard.putString("MESSAGE", "started");
        SmartDashboard.putString("Snap Turret MESSAGE", "");
        SmartDashboard.putString("Hood Angle MESSAGE", "");
        SmartDashboard.putString("Shooter Speed MESSAGE", "");
        // SmartDashboard.putData(CommandScheduler.getInstance());

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
        // SmartDashboard.putNumber(DashboardMap.kTrajectoryTyOffset, Vision.kCameraTYOffset);
        SmartDashboard.putBoolean(DashboardMap.kHoodUseManualAngle, false);
        SmartDashboard.putNumber(DashboardMap.kHoodManualAngle, 0.5);
        SmartDashboard.putBoolean(DashboardMap.kTurretUseManualAngle, false);
        SmartDashboard.putNumber(DashboardMap.kTurretManualAngle, 0.0);
        SmartDashboard.putBoolean(DashboardMap.kShooterUseManualSpeed, false);
        SmartDashboard.putNumber(DashboardMap.kShooterManualSpeed, 1500);
        SmartDashboard.putNumber(DashboardMap.kShooterAcceleratorSpeed, Shooter.kAcceleratorSpeed);
        SmartDashboard.putNumber("Shooter/P", 0.0005);
        SmartDashboard.putNumber("Shooter/FF", 0.0008);
        SmartDashboard.putNumber("Shooter/I", 0.0);
        SmartDashboard.putNumber("Shooter/D", 0.00002);
        SmartDashboard.putBoolean("Field Centric", true);
        SmartDashboard.putNumber("Shooter/Multi", 1.195);
        SmartDashboard.putNumber("Shooter/Entry Angle", -70);
        SmartDashboard.putBoolean("Only Drive Auto", false);
        SmartDashboard.putNumber("Shooter/TY Offset", Vision.kCameraTYOffset);
    }

    @Override
    public void robotPeriodic() {
        if (m_initTicks > 0)
        {
            m_initTicks++;
            if (m_initTicks == 100)
            {
                m_initTicks = -1;
                m_robotContainer.hood.initialization();
                m_robotContainer.drivetrain.setInitalPositions();
                m_robotContainer.drivetrain.resetNavX();
                m_robotContainer.turret.resetEncoder();
                SmartDashboard.putString("MESSAGE", "initialized");
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
        if(SmartDashboard.getBoolean("Only Drive Auto", false)) {
            m_autoCommandOnly.schedule();
        } else {

            m_autoCommand.schedule();
        }
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
