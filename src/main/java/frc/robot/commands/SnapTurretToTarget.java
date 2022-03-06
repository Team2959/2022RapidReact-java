package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

/** Snaps Turret to Target */
public class SnapTurretToTarget extends CommandBase {
    private final RobotContainer m_container;
    double m_target = 0.0;

    public SnapTurretToTarget(RobotContainer container) {
        m_container = container;

        addRequirements(m_container.turret);
    }

    @Override
    public void initialize() {
        // System.err.println("Started SnapToTarget");
        m_target = m_container.turret.getAngleDegrees() + m_container.vision.getTX();
        m_container.turret.setSpeedToTargetAngle(m_target);
    }

    @Override
    public void execute() {
        // KFR: maybe continuously update m_target for as turret turns, better resolution of true tx
        // m_target = m_container.turret.getAngleDegrees() + m_container.vision.getTX();
        // m_container.turret.setSpeedToTargetAngle(m_target);

        // Bryce: Continuous sweep code to bounce back/forth to extremes and find target
        // if(m_container.turret.getAngleDegrees() <= Turret.kMaxDegreesBackwards) {
        //     m_container.turret.setSpeed(kSpeed);
        // }
        // else if(m_container.turret.getAngleDegrees() >= Turret.kMaxDegreesForwards) {
        //     m_container.turret.setSpeed(-kSpeed);
        // }
    }

    @Override
    public boolean isFinished() {
        return m_container.turret.isCloseEnoughToTarget(m_target);
    }
    
    @Override
    public void end(boolean interupt) {
        // System.err.println("Ended SnapToTarget");
        m_container.turret.setSpeed(0.0);
    }
}
