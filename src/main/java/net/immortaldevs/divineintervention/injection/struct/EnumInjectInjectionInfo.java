package net.immortaldevs.divineintervention.injection.struct;

import net.immortaldevs.divineintervention.injection.EnumInject;
import net.immortaldevs.divineintervention.injection.invoke.EnumInjectInjector;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

@InjectionInfo.AnnotationType(EnumInject.class)
@InjectionInfo.HandlerPrefix("enumfactory")
public class EnumInjectInjectionInfo extends InjectionInfo {
    public EnumInjectInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new EnumInjectInjector(this, Annotations.getValue(injectAnnotation, "name"));
    }
}
