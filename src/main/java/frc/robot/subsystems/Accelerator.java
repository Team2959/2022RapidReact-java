// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class Accelerator extends SubsystemBase {
  public static final double kSpeed = 0.25;
  private final CANSparkMax m_motor;

  /** Creates a new AcceleratorSubsystem. */
  public Accelerator() {
    m_motor = new CANSparkMax(19, CANSparkMax.MotorType.kBrushless);
    m_motor.restoreFactoryDefaults();
    m_motor.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed)
  {
    m_motor.set(speed);
  }
}
