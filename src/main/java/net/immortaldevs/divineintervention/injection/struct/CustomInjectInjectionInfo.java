package net.immortaldevs.divineintervention.injection.struct;

import net.immortaldevs.divineintervention.injection.CustomInject;
import net.immortaldevs.divineintervention.injection.CustomInjector;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

@InjectionInfo.AnnotationType(CustomInject.class)
@InjectionInfo.HandlerPrefix("custom")
public class CustomInjectInjectionInfo extends InjectionInfo {
    public CustomInjectInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        try {
            return (CustomInjector) Class.forName(Annotations.<Type>getValue(injectAnnotation, "injector")
                            .getInternalName()
                            .replace('/', '.'))
                    .getConstructor(InjectionInfo.class)
                    .newInstance(this);
        } catch (Exception e) {
            throw new InvalidInjectionException(this, e);
        }
    }

    @Override
    protected String getDescription() {
        return "Custom injection method";
    }
}
