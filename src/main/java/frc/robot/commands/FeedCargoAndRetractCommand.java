package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;

public class FeedCargoAndRetractCommand extends WaitCommand {

    private final Shooter m_shooter;

    public FeedCargoAndRetractCommand(Shooter shooter, double seconds) {
        super(seconds);

        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        super.initialize();
        m_shooter.setFeeder(true);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        m_shooter.setFeeder(false);
    }
}
