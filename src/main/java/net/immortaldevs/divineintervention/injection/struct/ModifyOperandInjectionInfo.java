package net.immortaldevs.divineintervention.injection.struct;

import net.immortaldevs.divineintervention.injection.ModifyOperand;
import net.immortaldevs.divineintervention.injection.invoke.ModifyOperandInjector;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.AnnotationType(ModifyOperand.class)
@InjectionInfo.HandlerPrefix("operand")
public class ModifyOperandInjectionInfo extends InjectionInfo {
    public ModifyOperandInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new ModifyOperandInjector(this);
    }

    @Override
    protected String getDescription() {
        return "Operand modifier method";
    }
}
