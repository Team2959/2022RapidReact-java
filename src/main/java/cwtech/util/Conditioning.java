package cwtech.util;

public class Conditioning {
    private double m_deadband = 0.1;
    private double m_power = 1.0;
    private double m_min = 0.0;
    private double m_max = 1.0;
    private double m_range = 1.0;
    private double m_mult = 0.0;

    public Conditioning() {
    }

    public void setDeadband(double deadband) {
        deadband = Math.abs(deadband);
        deadband = Math.min(deadband, 1.0);

        m_deadband = deadband;

        precompute();
    }

    public void setExponent(double exp) {
        exp = Math.max(exp, 0.0);

        m_power = exp;

        precompute();
    }

    public void setRange(double min, double max) {
        m_min = Math.min(min, max);
        m_max = Math.max(min, max);

        precompute();
    }

    public void precompute() {
        m_mult = 1.0 / (1.0 / m_deadband);
        m_range = m_max - m_min;
    }

    public double condition(double x) {
        double xa = Math.abs(x);
        double xs = JSCSgn(x);
        if(xa < m_deadband) {
            return 0;
        }
        else {
            return xs * ((JSCPower((xa - m_deadband) * m_mult, m_power) * m_range) + m_min);
        }

    }

    static double booleanToDouble(boolean value) {
        return value ? 1.0 : 0.0;
    }

    static boolean longToBoolean(long value) {
        return value != 0;
    }

    static double JSCSgn(double num) {
        return booleanToDouble(0.0 < num) - booleanToDouble(num < 0.0);
    }

    static double JSCPower(double base, double power) {
        long ipart = (long)power;
        double fpart = power - ipart;
        if(ipart == 0) {
            return base;
        }
        else if(ipart == 1) {
            return base * (fpart*base + (1-fpart));
        }
        else if(ipart == 2) {
            return base*base * (fpart*base + (1-fpart));
        }
        else if(ipart == 3) {
            return base*base*base * (fpart*base + (1-fpart));
        }
        else {
            double result = 1.0;
            while (longToBoolean(--ipart)) result *= base;
            return result * (fpart*base + (1-fpart));
        }
    }
}
