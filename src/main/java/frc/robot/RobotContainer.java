// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.Climb;
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
    public final OI oi = new OI(this);

    public RobotContainer() {
        drivetrain.setDefaultCommand(new TeleopDriveCommand(this, true));
      
        SmartDashboard.putData("Climb", climb);
        SmartDashboard.putData("Drivetrain", drivetrain);
        SmartDashboard.putData("Hood", hood);
        SmartDashboard.putData("Shooter", shooter);
        SmartDashboard.putData("Turret", turret);
        SmartDashboard.putData("Vision", vision);
        SmartDashboard.putData("Intake", intake);
    }
}
