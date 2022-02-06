package net.immortaldevs.parmesan.injection.invoke;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes.InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.struct.Target.Extension;

public class GrabOperandInjector extends Injector {
    public GrabOperandInjector(InjectionInfo info) {
        super(info, "@GrabOperand");
    }

    @Override
    protected void inject(Target target, InjectionNode node) {
        System.out.println(target.method.name);

        InjectorData handler = new InjectorData(target);
        this.validateParams(handler, this.returnType, this.returnType);

        InsnList injectedInsns = new InsnList();
        Extension extraStack = target.extendStack();

        if (!this.isStatic) {
            injectedInsns.add(new VarInsnNode(Opcodes.ALOAD, 0));
            if (this.returnType.getSize() == 1) {
                injectedInsns.add(new InsnNode(Opcodes.SWAP));
                extraStack.add();
            } else {
                injectedInsns.add(new InsnNode(Opcodes.DUP_X2));
                injectedInsns.add(new InsnNode(Opcodes.POP));
                extraStack.add(2);
                if (handler.captureTargetArgs == 0) extraStack.add();
            }
        }

        if (handler.captureTargetArgs != 0) {
            this.pushArgs(target.arguments,
                    injectedInsns,
                    target.getArgIndices(),
                    0,
                    handler.captureTargetArgs,
                    extraStack);
        }

        this.invokeHandler(injectedInsns);
        extraStack.apply();
        target.insns.insert(node.getCurrentTarget(), injectedInsns);
    }
}
