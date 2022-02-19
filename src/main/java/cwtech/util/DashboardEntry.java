package cwtech.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardEntry {
    private final String key;
    public DashboardEntry(String key) {
        this.key = key;
    }

    public DashboardEntry(String key, int value) {
        this.key = key;
        putNumber(value);
    }

    public DashboardEntry(String key, String value) {
        this.key = key;
        putString(value);
    }

    public DashboardEntry(String key, boolean value) {
        this.key = key;
        putBoolean(value);
    }
    
    public void putNumber(double value) {
        SmartDashboard.putNumber(this.key, value);
    }

    public double getNumber(double defaultValue) {
        return SmartDashboard.getNumber(this.key, defaultValue);
    }

    public void putString(String value) {
        SmartDashboard.putString(this.key, value);
    }

    public String getString(String defaultValue) {
        return SmartDashboard.getString(this.key, defaultValue);
    }

    public void putBoolean(boolean value) {
        SmartDashboard.putBoolean(this.key, value);
    }

    public boolean getBoolean(boolean defaultValue) {
        return SmartDashboard.getBoolean(this.key, defaultValue);
    }
}