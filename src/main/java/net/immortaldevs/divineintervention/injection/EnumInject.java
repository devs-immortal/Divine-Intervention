package net.immortaldevs.divineintervention.injection;

import org.spongepowered.asm.mixin.injection.At;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumInject {
    String name();

    String[] method();

    At at();

    boolean remap() default true;
}
