package frc.robot;

// Java do code sa ateema

import cwtech.util.Conditioning;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.simulation.JoystickSim;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ExtendClimbHooksCommand;
import frc.robot.commands.FireCommand;
import frc.robot.commands.IntakeToggleCommand;
import frc.robot.commands.RetractClimbHooksCommand;
import frc.robot.commands.ReverseIntakeCommand;
import frc.robot.commands.SetHoodAngleCommand;
import frc.robot.commands.TurnTurretToPositionCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;

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
    private final JoystickButton m_hoodDownButton;
    private final JoystickButton m_testButton;

    public OI(RobotContainer container) {
        m_container = container;

        m_xConditioning = new Conditioning();
        m_yConditioning = new Conditioning();
        m_rotationConditioning = new Conditioning();

        m_xConditioning.setDeadband(0.15);
        m_xConditioning.setExponent(1.7);
        m_yConditioning.setDeadband(0.15);
        m_yConditioning.setExponent(1.4);
        m_rotationConditioning.setDeadband(0.2);
        m_rotationConditioning.setExponent(0.9);

        m_leftJoystick = new Joystick(RobotMap.kLeftJoystick);
        m_rightJoystick = new Joystick(RobotMap.kRightJoystick);
        m_buttonBox = new Joystick(RobotMap.kButtonBox);

        m_toggleIntakeButton = new JoystickButton(m_rightJoystick, RobotMap.kToggleIntakeButton);
        m_reverseIntakeButton = new JoystickButton(m_buttonBox, RobotMap.kReverseIntakeButton);
        m_extendClimbHooksButton = new JoystickButton(m_buttonBox, RobotMap.kExtendClimbHooksButton);
        m_retractClimbHooksButton = new JoystickButton(m_buttonBox, RobotMap.kRetractClimbHooksButton);
        m_fireButton = new JoystickButton(m_buttonBox, RobotMap.kFireButton);
        m_hoodDownButton = new JoystickButton(m_buttonBox, RobotMap.kHoodDownButton);
        m_testButton = new JoystickButton(m_buttonBox, RobotMap.kTestButton);

        m_toggleIntakeButton.whenPressed(new IntakeToggleCommand(m_container));
        m_reverseIntakeButton.whileHeld(new ReverseIntakeCommand(m_container));
        m_extendClimbHooksButton.whenPressed(new ExtendClimbHooksCommand(m_container));
        m_retractClimbHooksButton.whenPressed(new RetractClimbHooksCommand(m_container));
        m_fireButton.whenPressed(new FireCommand(m_container));
        m_hoodDownButton.whenPressed(new SetHoodAngleCommand(m_container, Hood.kMinDegrees));
        m_testButton.whenPressed(new TurnTurretToPositionCommand(m_container));
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

    public DriveState getDriveState() {
        double x = m_leftJoystick.getX();
        x = -m_xConditioning.condition(x);
        double xSpeed = x * Drivetrain.kMaxSpeedMetersPerSecond;
        
        double y = m_leftJoystick.getY();
        y = m_yConditioning.condition(y);
        double ySpeed = y * Drivetrain.kMaxSpeedMetersPerSecond;

        double r = m_rightJoystick.getX();
        r = m_rotationConditioning.condition(r);
        double rotation = -r * Drivetrain.kMaxAngularSpeedRadiansPerSecond;

        return new DriveState(xSpeed, ySpeed, rotation);
    }

    public void onDisabledInit() {
        // m_xConditioning.setExponent(SmartDashboard.getNumber(DashboardMap.kOIXExponent, 0.8));
        // m_xConditioning.setDeadband(SmartDashboard.getNumber(DashboardMap.kOIXDeadband, 0.15));
        // m_yConditioning.setExponent(SmartDashboard.getNumber(DashboardMap.kOIYExponent, 0.8));
        // m_yConditioning.setDeadband(SmartDashboard.getNumber(DashboardMap.kOIYDeadband, 0.15));
        // m_rotationConditioning.setExponent(SmartDashboard.getNumber(DashboardMap.kOITurnExponent, 0.8));
        // m_rotationConditioning.setDeadband(SmartDashboard.getNumber(DashboardMap.kOITurnDeadband, 0.25));
    }
}
