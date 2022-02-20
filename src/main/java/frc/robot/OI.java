package frc.robot;

// Java do code sa ateema

import cwtech.util.Conditioning;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ExtendClimbHooksCommand;
import frc.robot.commands.IntakeToggleCommand;
import frc.robot.commands.RetractClimbHooksCommand;
import frc.robot.commands.ReverseIntakeCommand;
import frc.robot.subsystems.Drivetrain;

public class OI {
    private Joystick leftJoystick;
    private Joystick rightJoystick;
    private Joystick buttonBox;
    private final RobotContainer container;

    private Conditioning xConditioning;
    private Conditioning yConditioning;
    private Conditioning rotationConditioning;

    private final JoystickButton toggleIntakeButton;
    private final JoystickButton reverseIntakeButton;
    private final JoystickButton extendClimbHooksButton;
    private final JoystickButton retractClimbHooksButton;

    public OI(RobotContainer container) {
        this.leftJoystick = new Joystick(RobotMap.kLeftJoystick);
        this.rightJoystick = new Joystick(RobotMap.kRightJoystick);
        this.buttonBox = new Joystick(RobotMap.kButtonBox);
        this.xConditioning = new Conditioning();
        this.yConditioning = new Conditioning();
        this.rotationConditioning = new Conditioning();
        this.container = container;

        this.toggleIntakeButton = new JoystickButton(this.leftJoystick, RobotMap.kToggleIntakeButton); // TODO ensure proper joystick here
        this.reverseIntakeButton = new JoystickButton(this.buttonBox, RobotMap.kReverseIntakeButton);
        this.extendClimbHooksButton = new JoystickButton(this.buttonBox, RobotMap.kExtendClimbHooksButton);
        this.retractClimbHooksButton = new JoystickButton(this.buttonBox, RobotMap.kRetractClimbHooksButton);

        this.xConditioning.setDeadband(0.15);
        this.xConditioning.setExponent(1.3);
        this.yConditioning.setDeadband(0.15);
        this.yConditioning.setExponent(0.8);
        this.rotationConditioning.setDeadband(0.25);
        this.rotationConditioning.setExponent(0.8);

        this.toggleIntakeButton.whenPressed(new IntakeToggleCommand(this.container));
        this.reverseIntakeButton.whenPressed(new ReverseIntakeCommand(this.container));
        this.extendClimbHooksButton.whenPressed(new ExtendClimbHooksCommand(this.container));
        this.retractClimbHooksButton.whenPressed(new RetractClimbHooksCommand(this.container));
    }
    
    public class DriveState {
        public final double xMetersPerSecond;
        public final double yMetersPerSecond;
        public final double rotationRadiansPerSecond;
        DriveState(double xMetersPerSecond, double yMetersPerSecond, double rotationRadiansPerSecond) {
            this.xMetersPerSecond = xMetersPerSecond;
            this.yMetersPerSecond = yMetersPerSecond;
            this.rotationRadiansPerSecond = rotationRadiansPerSecond;
        }
    }

    public enum DriveType {
        Double,
        Single,
    }

    public DriveState getDriveState(DriveType driveType) {
        double x = 0, y = 0, rot = 0;
        if(driveType == DriveType.Double) {
            x = this.leftJoystick.getX();
            y = this.leftJoystick.getY();
            rot = this.rightJoystick.getX();
        }
        else if(driveType == DriveType.Single) {
            x = this.rightJoystick.getX();
            y = this.rightJoystick.getY();
            rot = this.rightJoystick.getTwist();
        }
        double xSpeed = (-this.xConditioning.condition(x)) * Drivetrain.kMaxSpeedMetersPerSecond;
        double ySpeed = (this.yConditioning.condition(y)) * Drivetrain.kMaxSpeedMetersPerSecond;
        double rotationSpeed = (this.rotationConditioning.condition(rot)) * Drivetrain.kMaxAngularSpeedRadiansPerSecond;
        return new DriveState(xSpeed, ySpeed, rotationSpeed);
    }

}
