package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SetHoodAngleCommand extends CommandBase {
    private final RobotContainer container;
    private final double hoodPosition;
    private boolean backwards;
    public SetHoodAngleCommand(RobotContainer container, double hoodPosition) { 
        this.container = container;
        this.hoodPosition = hoodPosition;
        this.backwards = false;

        addRequirements(this.container.hood);
    }

    @Override
    public void initialize() {
        double hoodPosition = this.container.hood.getPosition();

        if(hoodPosition > this.hoodPosition) {
            this.container.hood.setSpeed(1.0);
            this.backwards = false;
        } 
        else {
            this.container.hood.setSpeed(-1.0);
            this.backwards = true;
        }
    }

    @Override
    public boolean isFinished() {
        double currentHoodPosition = this.container.hood.getPosition();
        // Checks to see if at or pass requested hood position and if so stop
        return this.backwards ? currentHoodPosition <= this.hoodPosition : currentHoodPosition >= this.hoodPosition;
    }
    
}
