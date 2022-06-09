package net.immortaldevs.divineintervention.injection;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * You're on your own.
 */
@SuppressWarnings("unused")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomInject {
    String[] method() default {};

    Slice[] slice() default {};

    At[] at();

    Class<? extends CustomInjector> injector();

    boolean remap() default true;

    int require() default -1;

    int expect() default 1;

    int allow() default -1;
}
