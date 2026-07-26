package rusplugins.neonukkitx.command.simple;

import rusplugins.neonukkitx.command.data.CommandParamType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nilsbrychzy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
    String name();

    CommandParamType type() default CommandParamType.RAWTEXT;

    boolean optional() default false;
}
