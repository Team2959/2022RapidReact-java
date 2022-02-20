package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;

/** Snaps Turret to Target */
public class SnapTurretToTarget extends CommandBase {
    private final RobotContainer container;

    public SnapTurretToTarget(RobotContainer container) {
        this.container = container;

        addRequirements(this.container.turret);
        addRequirements(this.container.vision);
    }

    private final double kSpeed = 0.5;
    private final double kVisionError = 1;

    @Override
    public void initialize() {
        double tx = this.container.vision.getTX();
        if(tx > 0) {
            this.container.turret.setSpeed(kSpeed);
        }
        else {
            this.container.turret.setSpeed(-kSpeed);
        }
    }

    @Override
    public void execute() {
        if(this.container.turret.getAngleDegrees() <= Turret.kMaxDegreesBackwards) {
            this.container.turret.setSpeed(kSpeed);
        }
        else if(this.container.turret.getAngleDegrees() >= Turret.kMaxDegreesForwards) {
            this.container.turret.setSpeed(-kSpeed);
        }
    }

    @Override
    public boolean isFinished() {
        return this.container.vision.getTX() < kVisionError || this.container.vision.getTX() > -kVisionError;
    }
    
    @Override
    public void end(boolean interupt) {
        this.container.turret.setSpeed(0);
    }
}
