package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CargoFeeder;

public class FeedCargoAndRetractCommand extends WaitCommand {

    private final CargoFeeder m_cargoFeeder;

    public FeedCargoAndRetractCommand(CargoFeeder cargoFeeder, double seconds) {
        super(seconds);

        m_cargoFeeder = cargoFeeder;
        addRequirements(m_cargoFeeder);
    }

    @Override
    public void initialize() {
        super.initialize();
        m_cargoFeeder.setFeeder(true);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        m_cargoFeeder.setFeeder(false);
    }
}
