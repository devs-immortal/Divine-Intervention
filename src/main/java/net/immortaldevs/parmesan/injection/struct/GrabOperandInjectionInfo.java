package net.immortaldevs.parmesan.injection.struct;

import net.immortaldevs.parmesan.injection.GrabOperand;
import net.immortaldevs.parmesan.injection.invoke.GrabOperandInjector;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.AnnotationType(GrabOperand.class)
@InjectionInfo.HandlerPrefix("operand")
public class GrabOperandInjectionInfo extends InjectionInfo {
    public GrabOperandInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new GrabOperandInjector(this);
    }

    @Override
    protected String getDescription() {
        return "Operand word modifier method";
    }
}
