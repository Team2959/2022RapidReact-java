package frc.robot;

// Java do code sa ateema

import cwtech.util.Conditioning;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ExtendClimbHooksCommand;
import frc.robot.commands.FireCommand;
import frc.robot.commands.GloryShotCommand;
import frc.robot.commands.IntakeToggleCommand;
import frc.robot.commands.LowGoalWallShotCommandGroup;
import frc.robot.commands.PreloadDoubleCargoCommandGroup;
import frc.robot.commands.RetractClimbHooksCommand;
import frc.robot.commands.ReverseAccelaratorCommand;
import frc.robot.commands.ReverseIntakeCommand;
// import frc.robot.commands.SafeZoneShotCommandGroup;
import frc.robot.commands.SetHoodAngleCommand;
// import frc.robot.commands.TurnTurretToPositionCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Shooter;

public class OI {
    public static final double kDriverXdeadband = 0.15;
    public static final double kDriverXexponent = 1.7;
    public static final double kDriverYdeadband = 0.15;
    public static final double kDriverYexponent = 1.4;
    public static final double kDriverRotationDeadband = 0.2;
    public static final double kDriverRotationExponent = 0.9;

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
    private final JoystickButton m_safeZoneShotButton;
    private final JoystickButton m_wallShotButton;
    private final JoystickButton m_gloryShotButton;
    private final JoystickButton m_reverseAccButton;
    private final JoystickButton m_hoodUpButton;
    private final JoystickButton m_fireOverride;
    private final JoystickButton m_toggleIntakeButtonLeft;

    public OI(RobotContainer container) {
        m_container = container;

        m_xConditioning = new Conditioning();
        m_yConditioning = new Conditioning();
        m_rotationConditioning = new Conditioning();

        m_xConditioning.setDeadband(kDriverXdeadband);
        m_xConditioning.setExponent(kDriverXexponent);
        m_yConditioning.setDeadband(kDriverYdeadband);
        m_yConditioning.setExponent(kDriverYexponent);
        m_rotationConditioning.setDeadband(kDriverRotationDeadband);
        m_rotationConditioning.setExponent(kDriverRotationExponent);

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
        m_safeZoneShotButton = new JoystickButton(m_buttonBox, RobotMap.kSafeZoneShotButton);
        m_wallShotButton = new JoystickButton(m_buttonBox, RobotMap.kWallShotButton);
        m_gloryShotButton = new JoystickButton(m_buttonBox, RobotMap.kGloryShotButton);
        m_reverseAccButton = new JoystickButton(m_buttonBox, RobotMap.kReverseAccButton);
        m_hoodUpButton = new JoystickButton(m_buttonBox, RobotMap.kHoodUpButton);
        m_fireOverride = new JoystickButton(m_buttonBox, RobotMap.kFireOverrideButton);
        m_toggleIntakeButtonLeft = new JoystickButton(m_leftJoystick, 1);

        m_toggleIntakeButton.whenPressed(new IntakeToggleCommand(m_container));
        m_toggleIntakeButtonLeft.whenPressed(new IntakeToggleCommand(m_container));
        m_reverseIntakeButton.whileHeld(new ReverseIntakeCommand(m_container));
        m_extendClimbHooksButton.whenPressed(new ExtendClimbHooksCommand(m_container));
        m_retractClimbHooksButton.whileHeld(new RetractClimbHooksCommand(m_container));
        m_fireButton.whenPressed(new FireCommand(m_container));
        m_hoodDownButton.whenPressed(new SetHoodAngleCommand(m_container, Hood.kMinDegrees));
        // m_safeZoneShotButton.whenPressed(new SafeZoneShotCommandGroup(m_container));
        m_safeZoneShotButton.whenPressed(new PreloadDoubleCargoCommandGroup(m_container));
        m_wallShotButton.whenPressed(new LowGoalWallShotCommandGroup(m_container));
        m_testButton.whenPressed(new InstantCommand(() -> {
            m_container.shooter.setVelocity(Shooter.kIdleSpeed);
        }, m_container.shooter));
        m_gloryShotButton.whenPressed(new GloryShotCommand(m_container));
        m_reverseAccButton.whileHeld(new ReverseAccelaratorCommand(m_container));
        m_hoodUpButton.whenPressed(new SetHoodAngleCommand(m_container, 1));
    }

    public boolean getFireOverrided() {
        return m_fireOverride.get();
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
        double multiplier = Math.min(1.0, SmartDashboard.getNumber("Drive Reducer", 0.75));
        double x = m_leftJoystick.getY() * multiplier;
        x = m_xConditioning.condition(x);
        double xSpeed = x * Drivetrain.kMaxSpeedMetersPerSecond;
        
        double y = m_leftJoystick.getX() * multiplier;
        y = m_yConditioning.condition(y);
        double ySpeed = y * Drivetrain.kMaxSpeedMetersPerSecond;

        double r = m_rightJoystick.getX();
        r = m_rotationConditioning.condition(r);
            double rotation = r * Drivetrain.kMaxAngularSpeedRadiansPerSecond;

        return new DriveState(xSpeed, ySpeed, rotation);
    }

    public void onDisabledInit() {
        // m_xConditioning.setExponent(SmartDashboard.getNumber(DashboardMap.kOIXExponent, kDriverXexponent));
        // m_xConditioning.setDeadband(SmartDashboard.getNumber(DashboardMap.kOIXDeadband, kDriverXdeadband));
        // m_yConditioning.setExponent(SmartDashboard.getNumber(DashboardMap.kOIYExponent, kDriverYexponent));
        // m_yConditioning.setDeadband(SmartDashboard.getNumber(DashboardMap.kOIYDeadband, kDriverYdeadband));
        // m_rotationConditioning.setExponent(SmartDashboard.getNumber(DashboardMap.kOITurnExponent, kDriverRotationExponent));
        // m_rotationConditioning.setDeadband(SmartDashboard.getNumber(DashboardMap.kOITurnDeadband, kDriverRotationDeadband));
    }
}
