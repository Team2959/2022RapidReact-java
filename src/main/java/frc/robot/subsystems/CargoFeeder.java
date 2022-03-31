// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class CargoFeeder extends SubsystemBase {
  private final Solenoid m_feeder;

  public CargoFeeder() {
    m_feeder = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.kFeederSolenoid);
  }

  public void setFeeder(boolean extended) {
    m_feeder.set(extended);
  }

  public boolean getFeeder() {
    return m_feeder.get();
  }
}
