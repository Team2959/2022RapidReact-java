// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package cwtech.trigger;

import edu.wpi.first.wpilibj2.command.button.Trigger;

/** Add your docs here. */
public class ModeTrigger extends Trigger {
    public enum Mode {
        Disabled,
        Test,
        Teleop,
        Autonomous,
    };
    private static Mode m_currentMode;
    private final Mode m_mode;

    public ModeTrigger(Mode mode) {
        m_mode = mode;
    }

    @Override
    public boolean get() {
        return m_mode == m_currentMode;
    }

    public static void registerMode(Mode mode) {
        m_currentMode = mode;
    }
}
