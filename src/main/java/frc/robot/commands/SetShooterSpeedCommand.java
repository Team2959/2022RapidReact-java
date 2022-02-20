package frc.robot.commands;

import cwtech.util.Util;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SetShooterSpeedCommand extends CommandBase {
    private final RobotContainer container;
    private final double speed;

    public SetShooterSpeedCommand(RobotContainer container, double speed) {
        this.container = container;
        this.speed = speed;

        addRequirements(this.container.shooter);
    }

    @Override
    public void initialize() {
        this.container.shooter.setVelocity(this.speed);
        this.container.shooter.setAccelarator(0.5);
    }

    @Override
    public boolean isFinished() {
        return Util.dcompare(this.container.shooter.getVelocity(), this.speed);
    }
}
