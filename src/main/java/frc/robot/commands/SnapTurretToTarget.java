package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;

/** Snaps Turret to Target */
public class SnapTurretToTarget extends CommandBase {
    private final RobotContainer m_container;

    public SnapTurretToTarget(RobotContainer container) {
        m_container = container;

        addRequirements(m_container.turret);
        addRequirements(m_container.vision);
    }

    private final double kSpeed = 0.5;
    private final double kVisionError = 1;

    @Override
    public void initialize() {
        double tx = m_container.vision.getTX();
        if(tx > 0) {
            m_container.turret.setSpeed(-kSpeed);
        }
        else {
            m_container.turret.setSpeed(kSpeed);
        }
    }

    @Override
    public void execute() {
        if(m_container.turret.getAngleDegrees() <= Turret.kMaxDegreesBackwards) {
            m_container.turret.setSpeed(kSpeed);
        }
        else if(m_container.turret.getAngleDegrees() >= Turret.kMaxDegreesForwards) {
            m_container.turret.setSpeed(-kSpeed);
        }
    }

    @Override
    public boolean isFinished() {
        return m_container.vision.getTX() < kVisionError || m_container.vision.getTX() > -kVisionError;
    }
    
    @Override
    public void end(boolean interupt) {
        m_container.turret.setSpeed(0);
    }
}
