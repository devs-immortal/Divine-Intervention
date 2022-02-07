package net.immortaldevs.divineintervention.injection;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that this mixin method should inject a callback to itself in the
 * target method identified by {@link #method}. The callback will
 * receive the ordinal of the next enum constant in the enum class,
 * and must return a new instance of the enum class with the same name as
 * specified in {@link #name} and the ordinal it was passed.
 *
 * <p>Although (due to this being an injector, which mixin has hardcoded
 * rules for) you have flexibility in specifying the injection point, this
 * injector MUST match ONLY ONE injection point, that injection point
 * MUST be an invocation of a method which returns an array of instances
 * of the target class, and that invocation MUST take place within
 * the static constructor.</p>
 */
@SuppressWarnings("unused")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumInject {
    /**
     * The name of the field for the injected enum constant,
     * using CONSTANT_NAMING_CONVENTIONS.
     *
     * @return name for the injected field
     */
    String name();

    /**
     * String representation of one or more
     * {@link ITargetSelector target selectors} which identify the target
     * methods.
     *
     * @return target method(s) for this injector
     */
    String[] method() default {};

    /**
     * {@link At} annotation which describes the
     * {@link InjectionPoint} in the target method. Allows a
     * callback to be injected in the target method.
     *
     * @return injection point specifier for this injector
     */
    At at();

    /**
     * By default, the annotation processor will attempt to locate an
     * obfuscation mapping for all {@link EnumInject} methods since it is
     * anticipated that in general the target of a {@link EnumInject}
     * annotation will be an obfuscated method in the target class. However
     * since it is possible to also apply mixins to non-obfuscated targets (or
     * non- obfuscated methods in obfuscated targets, such as methods added by
     * mods) it may be necessary to suppress the compiler error which would
     * otherwise be generated. Setting this value to <em>false</em> will cause
     * the annotation processor to skip this annotation when attempting to build
     * the obfuscation table for the mixin.
     *
     * @return True to instruct the annotation processor to search for
     *      obfuscation mappings for this annotation
     */
    boolean remap() default true;
}
