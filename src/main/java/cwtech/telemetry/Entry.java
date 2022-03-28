package cwtech.telemetry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Entry {
    public String key();
    public boolean writeable() default false;
    public boolean defaultBoolean() default false;
    public double defaultDouble() default 0.0;
    public String defaultString() default "";
    // public boolean readable() default false;
}
