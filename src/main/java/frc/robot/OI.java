package frc.robot;

// Java do code sa ateema

import cwtech.util.Conditioning;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ExtendClimbHooksCommand;
import frc.robot.commands.FireCommand;
import frc.robot.commands.IntakeToggleCommand;
import frc.robot.commands.RetractClimbHooksCommand;
import frc.robot.commands.ReverseIntakeCommand;
import frc.robot.subsystems.Drivetrain;

public class OI {
    private Joystick m_leftJoystick;
    private Joystick m_rightJoystick;
    private Joystick m_buttonBox;
    private final RobotContainer m_container;

    private Conditioning m_xConditioning;
    private Conditioning m_yConditioning;
    private Conditioning m_rotationConditioning;

    private final JoystickButton m_toggleIntakeButton;
    private final JoystickButton m_reverseIntakeButton;
    private final JoystickButton m_extendClimbHooksButton;
    private final JoystickButton m_retractClimbHooksButton;
    private final JoystickButton m_fireButton;

    public OI(RobotContainer container) {
        m_leftJoystick = new Joystick(RobotMap.kLeftJoystick);
        m_rightJoystick = new Joystick(RobotMap.kRightJoystick);
        m_buttonBox = new Joystick(RobotMap.kButtonBox);
        m_xConditioning = new Conditioning();
        m_yConditioning = new Conditioning();
        m_rotationConditioning = new Conditioning();
        m_container = container;

        m_toggleIntakeButton = new JoystickButton(m_rightJoystick, RobotMap.kToggleIntakeButton);
        m_reverseIntakeButton = new JoystickButton(m_buttonBox, RobotMap.kReverseIntakeButton);
        m_extendClimbHooksButton = new JoystickButton(m_buttonBox, RobotMap.kExtendClimbHooksButton);
        m_retractClimbHooksButton = new JoystickButton(m_buttonBox, RobotMap.kRetractClimbHooksButton);
        m_fireButton = new JoystickButton(m_buttonBox, RobotMap.kFireButton);

        m_xConditioning.setDeadband(0.15);
        m_xConditioning.setExponent(1.3);
        m_yConditioning.setDeadband(0.15);
        m_yConditioning.setExponent(0.8);
        m_rotationConditioning.setDeadband(0.25);
        m_rotationConditioning.setExponent(0.8);

        m_toggleIntakeButton.whenPressed(new IntakeToggleCommand(m_container));
        m_reverseIntakeButton.whileHeld(new ReverseIntakeCommand(m_container));
        m_extendClimbHooksButton.whenPressed(new ExtendClimbHooksCommand(m_container));
        m_retractClimbHooksButton.whenPressed(new RetractClimbHooksCommand(m_container));
        m_fireButton.whenPressed(new FireCommand(m_container));
    }
    
    public class DriveState {
        public final double m_xMetersPerSecond;
        public final double m_yMetersPerSecond;
        public final double m_rotationRadiansPerSecond;
        DriveState(double xMetersPerSecond, double yMetersPerSecond, double rotationRadiansPerSecond) {
            m_xMetersPerSecond = xMetersPerSecond;
            m_yMetersPerSecond = yMetersPerSecond;
            m_rotationRadiansPerSecond = rotationRadiansPerSecond;
        }
    }

    public enum DriveType {
        Double,
        Single,
    }

    public DriveState getDriveState(DriveType driveType) {
        double xSpeed = 0, ySpeed = 0, rotation = 0;

        if(driveType == DriveType.Double) {
            double x = m_leftJoystick.getX();
            x = -m_xConditioning.condition(x);
            xSpeed = x * Drivetrain.kMaxSpeedMetersPerSecond;
            
            double y = m_leftJoystick.getY();
            y = m_yConditioning.condition(y);
            ySpeed = y * Drivetrain.kMaxSpeedMetersPerSecond;

            double r = m_rightJoystick.getX();
            r = m_rotationConditioning.condition(r);
            rotation = r * Drivetrain.kMaxAngularSpeedRadiansPerSecond;
        }

        return new DriveState(xSpeed, ySpeed, rotation);
    }

}
