package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

/** Snaps Turret to Target */
public class SnapTurretToTarget extends CommandBase {
    private final RobotContainer container;

    public SnapTurretToTarget(RobotContainer container) {
        this.container = container;

        addRequirements(this.container.turret);
        addRequirements(this.container.vision);
    }

    @Override
    public void initialize() {
        double tx = this.container.vision.getTX();
        if(tx > 0) {
            this.container.turret.setSpeed(0.5);
        }
        else {
            this.container.turret.setSpeed(-0.5);
        }
    }

    @Override
    public void execute() {
    }
    
}
