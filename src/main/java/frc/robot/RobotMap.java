// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class RobotMap {

    // CAN motor addresses
    public static final int kFrontLeftDriveCANSparkMaxMotor = 1;
    public static final int kFrontRightDriveCANSparkMaxMotor = 2;
    public static final int kBackRightDriveCANSparkMaxMotor = 3;
    public static final int kBackLeftDriveCANSparkMaxMotor = 4;
    public static final int kShooterPrimaryCANSparkMaxMotor = 5;
    public static final int kShooterFollowerCANSparkMaxMotor = 6;
    public static final int kTurretCANSparkMaxMotor = 7;
    public static final int kIntakeCANSparkMaxMotor = 8;
    public static final int kAccelaratorVictorSPX = 9;
    public static final int kFrontLeftTurnCANSparkMaxMotor = 11;
    public static final int kFrontRightTurnCANSparkMaxMotor = 12;
    public static final int kBackRightTurnCANSparkMaxMotor = 13;
    public static final int kBackLeftTurnCANSparkMaxMotor = 14;

    // PWM motor addresses
    // public static final int kClimbWheelsPwmSparkMotor = 1;

    // REV Pneumatic Hub solenoid addresses
    public static final int kFeederSolenoid = 0;
    public static final int kClimbSolenoid = 1;
    public static final int kIntakeArmsSolenoid = 2;

    // Servos
    public static final int kHoodServoRight = 1;
    public static final int kHoodServoLeft = 0;

    // Digital IO addresses
    public static final int kTurretPulseWidthDigIO = 3;
    public static final int kHoodPulseWidthDigIO = 4;
    public static final int kFrontLeftTurnPulseWidthDigIO = 5;
    public static final int kFrontRightTurnPulseWidthDigIO = 6;
    public static final int kBackRightTurnPulseWidthDigIO = 7;
    public static final int kBackLeftTurnPulseWidthDigIO = 8;

    // Operator input USB ports
    public static final int kLeftJoystick = 1;
    public static final int kRightJoystick = 2;
    public static final int kButtonBox = 3;

    // Co-Pilot Buttons
    public static final int kExtendClimbHooksButton = 4;
    public static final int kRetractClimbHooksButton = 5;
    public static final int kReverseIntakeButton = 10; 

    // Driver Buttons
    public static final int kToggleIntakeButton = 2;

    // Zeroed values, should be in radians
    public static final double kZeroedFrontLeft = 3.082;
    public static final double kZeroedFrontRight = 0.000;
    public static final double kZeroedBackLeft = 0.520;
    public static final double kZeroedBackRight = -0.230;
    public static final double kZeroedTurret = 0.0;

}
