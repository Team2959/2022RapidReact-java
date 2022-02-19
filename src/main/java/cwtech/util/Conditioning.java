package cwtech.util;

public class Conditioning {
    private double deadband = 0.1;
    private double power = 1.0;
    private double min = 0.0;
    private double max = 1.0;
    private double range = 1.0;
    private double mult = 0.0;

    public Conditioning() {
    }

    public void setDeadband(double deadband) {
        deadband = Math.abs(deadband);
        deadband = Math.min(deadband, 1.0);

        this.deadband = deadband;

        precompute();
    }

    public void setExponent(double exp) {
        exp = Math.max(exp, 0.0);

        this.power = exp;

        precompute();
    }

    public void setRange(double min, double max) {
        this.min = Math.min(min, max);
        this.max = Math.max(min, max);

        precompute();
    }

    public void precompute() {
        this.mult = 1.0 / (1.0 / this.deadband);
        this.range = this.max - this.min;
    }

    public double condition(double x) {
        double xa = Math.abs(x);
        double xs = JSCSgn(x);
        if(xa < this.deadband) {
            return 0;
        }
        else {
            return xs * ((JSCPower((xa - this.deadband) * this.mult, this.power) * this.range) + this.min);
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
