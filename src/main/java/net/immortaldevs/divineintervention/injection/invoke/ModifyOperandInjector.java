package net.immortaldevs.divineintervention.injection.invoke;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes.InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.struct.Target.Extension;

import java.util.Arrays;
import java.util.List;

public class ModifyOperandInjector extends Injector {
    private final List<Integer> localIndices;

    public ModifyOperandInjector(InjectionInfo info, List<Integer> locals) {
        super(info, "@ModifyOperand");
        this.localIndices = locals == null ? List.of() : locals;
    }

    @Override
    protected void inject(Target target, InjectionNode node) {
        Type[] handlerArgs = Arrays.copyOf(this.methodArgs, this.localIndices.size() + 1);
        handlerArgs[0] = this.returnType;

        InjectorData handler = new InjectorData(target);
        this.validateParams(handler, this.returnType, handlerArgs);

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

        for (int i = 0; i < this.localIndices.size(); i++) {
            injectedInsns.add(new VarInsnNode(handlerArgs[i + 1].getOpcode(Opcodes.ILOAD),
                    this.localIndices.get(i)));
            extraStack.add();
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
        target.insns.insertBefore(node.getCurrentTarget(), injectedInsns);
    }
}
