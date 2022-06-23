package net.immortaldevs.divineintervention.injection;

import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that this mixin method should inject a callback to itself in the
 * target method(s) identified by {@link #method}. The callback will
 * receive the topmost word on the operand stack, and will leave
 * its return value in its place.
 *
 * <p><tt>ModifyOperand</tt> callbacks should always take one argument of the
 * type to capture and return the same type. For example a <tt>ModifyOperand
 * </tt> for an operand of type <ins>{@link String}</ins> should have the
 * signature:</p>
 *
 * <blockquote><code>private <ins>String</ins> myHandlerMethod(<ins>String</ins>
 * variable) {<br />&nbsp; &nbsp; ...</code></blockquote>
 *
 * <h4>Notes</h4>
 *
 * <p>Like other injectors, the callback signature can optionally include the
 * target method arguments by simply appending them to the handler method
 * signature.</p>
 */
@SuppressWarnings("unused")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyOperand {
    /**
     * String representation of one or more
     * {@link ITargetSelector target selectors} which identify the target
     * methods.
     *
     * @return target method(s) for this injector
     */
    String[] method() default {};

    /**
     * Array of {@link Slice} annotations which describe the method bisections
     * used in the {@link #at} queries for this injector.
     *
     * @return slices
     */
    Slice[] slice() default {};

    /**
     * Array of {@link At} annotations which describe the
     * {@link InjectionPoint}s in the target method. Allows one or more
     * callbacks to be injected in the target method.
     *
     * @return injection point specifiers for this injector
     */
    At[] at();

    /**
     * Specifies the local variable indices for this injector to capture.
     *
     * <p>When capturing local variables, the variables are appended after
     * the captured operand and before target method arguments.</p>
     *
     * @return the desired locals to capture
     */
    int[] locals() default {};

    /**
     * By default, the annotation processor will attempt to locate an
     * obfuscation mapping for all {@link ModifyOperand} methods since it is
     * anticipated that in general the target of a {@link ModifyOperand}
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

    /**
     * In general, injectors are intended to "fail soft" in that a failure to
     * locate the injection point in the target method is not considered an
     * error condition. Another transformer may have changed the method
     * structure or any number of reasons may cause an injection to fail. This
     * also makes it possible to define several injections to achieve the same
     * task given <em>expected</em> mutation of the target class and the
     * injectors which fail are simply ignored.
     *
     * <p>However, this behaviour is not always desirable. For example, if your
     * application depends on a particular injection succeeding you may wish to
     * detect the injection failure as an error condition. This argument is thus
     * provided to allow you to stipulate a <b>minimum</b> number of successful
     * injections for this callback handler. If the number of injections
     * specified is not achieved then an {@link InjectionError} is thrown at
     * application time. Use this option with care.</p>
     *
     * @return Minimum required number of injected callbacks, default specified
     *      by the containing config
     */
    int require() default -1;

    /**
     * Like {@link #require()} but only enabled if the
     * {@link MixinEnvironment.Option#DEBUG_INJECTORS mixin.debug.countInjections} option is set
     * to <tt>true</tt> and defaults to 1. Use this option during debugging to
     * perform simple checking of your injectors. Causes the injector to throw
     * a {@link InvalidInjectionException} if the expected number of injections
     * is not realised.
     *
     * @return Minimum number of <em>expected</em> callbacks, default 1
     */
    int expect() default 1;

    /**
     * Injection points are in general expected to match every candidate
     * instruction in the target method or slice, except in cases where options
     * such as {@link At#ordinal} are specified which naturally limit the number
     * of results.
     *
     * <p>This option allows for sanity-checking to be performed on the results
     * of an injection point by specifying a maximum allowed number of matches,
     * similar to that afforded by {@link Group#max}. For example if your
     * injection is expected to match 4 invocations of a target method, but
     * instead matches 5, this can become a detectable tamper condition by
     * setting this value to <tt>4</tt>.
     *
     * <p>Setting any value 1 or greater is allowed. Values less than 1 or less
     * than {@link #require} are ignored. {@link #require} supercedes this
     * argument such that if <tt>allow</tt> is less than <tt>require</tt> the
     * value of <tt>require</tt> is always used.</p>
     *
     * <p>Note that this option is not a <i>limit</i> on the query behaviour of
     * this injection point. It is only a sanity check used to ensure that the
     * number of matches is not too high
     *
     * @return Maximum allowed number of injections for this
     */
    int allow() default -1;
}
