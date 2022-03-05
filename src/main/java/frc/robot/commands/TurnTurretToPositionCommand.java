// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.DashboardMap;
import frc.robot.RobotContainer;

public class TurnTurretToPositionCommand extends CommandBase {
    RobotContainer m_container;
    double m_target = 0.0;
    /** Creates a new TurnTurretToPositionCommand. */
  public TurnTurretToPositionCommand(RobotContainer container) {
      m_container = container;
      addRequirements(m_container.turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // m_target = SmartDashboard.getNumber(DashboardMap.kTurretPosition, 0.0);
    m_target = m_container.turret.getAngleDegrees() + m_container.vision.getTX();
    double current = m_container.turret.getAngleDegrees();
    m_container.turret.setSpeed(m_target > current ? 0.2 : -0.2);
}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // m_container.turret.setDesiredAngle(m_target);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_container.turret.setSpeed(0.0);
    // m_container.turret.setDesiredAngle(m_container.turret.getAngleDegrees());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      double current = m_container.turret.getAngleDegrees();
      if (m_target < 0 && current < m_target)
        return true;
        if (m_target > 0 && current > m_target)
        return true;
    return Math.abs(current - m_target) < 2;
  }
}
