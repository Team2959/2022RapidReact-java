// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import cwtech.telemetry.Level;
import cwtech.telemetry.Manager;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutoComeForwardCommand;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.DriveOnlyAutoCommand;
import frc.robot.commands.RunPathCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.CargoFeeder;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class RobotContainer {
    public final Climb climb = new Climb();
    public final Drivetrain drivetrain = new Drivetrain();
    public final Hood hood = new Hood();
    public final Shooter shooter = new Shooter();
    public final Turret turret = new Turret();
    public final Vision vision = new Vision();
    public final Intake intake = new Intake();
    public final ColorSensor colorSensor = new ColorSensor();
    public final CargoFeeder cargoFeeder = new CargoFeeder();
    public final OI oi = new OI(this);

    public boolean m_activeTracking = false;

    private final AutoCommand m_autoCommand = new AutoCommand(this);
    private final AutoComeForwardCommand m_autoComeForwardCommand = new AutoComeForwardCommand(this);
    private final DriveOnlyAutoCommand m_driveOnlyAutoCommand = new DriveOnlyAutoCommand(this);
    private final RunPathCommand m_autoPathCommand = new RunPathCommand(this, "S-CURVE");
    public final SendableChooser<Command> m_autoChooser = new SendableChooser<Command>();

    private final Manager m_telemetryManager = new Manager();

    public RobotContainer() {
        drivetrain.setDefaultCommand(new TeleopDriveCommand(this, true));
        m_autoChooser.addOption("2 Ball Away", m_autoCommand);
        m_autoChooser.addOption("2 Ball Close", m_autoComeForwardCommand);
        m_autoChooser.setDefaultOption("Drive", m_driveOnlyAutoCommand);
        m_autoChooser.addOption("Path", m_autoPathCommand);
        SmartDashboard.putData("Auto", m_autoChooser);

        m_telemetryManager.register(drivetrain);
        m_telemetryManager.register(shooter);
        m_telemetryManager.setLevel(Optional.of(Level.Competition));
    }

    public void init() {
        m_telemetryManager.inital();
    }

    public void periodic() {
        m_telemetryManager.run();
    }
}
