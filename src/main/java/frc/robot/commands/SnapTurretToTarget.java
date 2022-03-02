package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;

/** Snaps Turret to Target */
public class SnapTurretToTarget extends CommandBase {
    private final RobotContainer m_container;
    private boolean m_forever;

    public SnapTurretToTarget(RobotContainer container) {
        m_container = container;

        m_forever = false;
        addRequirements(m_container.turret);
        addRequirements(m_container.vision);
    }

    public static SnapTurretToTarget createForever(RobotContainer container) {
        SnapTurretToTarget obj = new SnapTurretToTarget(container);
        obj.m_forever = true;
        return obj;
    }

    private final double kSpeed = 0.5;
    private final double kVisionError = 0.5;

    @Override
    public void initialize() {
        double tx = m_container.vision.getTX();
        if(tx > 0) {
            m_container.turret.setSpeed(kSpeed);
        }
        else {
            m_container.turret.setSpeed(-kSpeed);
        }
        m_container.turret.setSpeed(kSpeed);
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
        return !m_forever && (m_container.vision.getTX() < kVisionError || m_container.vision.getTX() > -kVisionError);
    }
    
    @Override
    public void end(boolean interupt) {
        m_container.turret.setSpeed(0);
    }
}
