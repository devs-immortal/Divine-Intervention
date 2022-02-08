package net.immortaldevs.divineintervention.injection.invoke;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Constants;

import java.util.Arrays;

public class EnumInjectInjector extends Injector {
    private final String name;

    public EnumInjectInjector(InjectionInfo info, String name) {
        super(info, "@EnumInject");
        this.name = name;
    }

    @Override
    protected void inject(Target target, InjectionNodes.InjectionNode node) {
        if (this.methodArgs.length != 1) {
            throw new InvalidInjectionException(this.info, "Wrong number of arguments. Expected 1, found " + this.methodArgs.length);
        }

        if (!Type.INT_TYPE.equals(this.methodArgs[0])) {
            throw new InvalidInjectionException(this.info, "Wrong type for argument 0. Expected int, found " + this.methodArgs[0].getClassName());
        }

        if (!(node.getOriginalTarget() instanceof MethodInsnNode methodInsnNode)
                || !methodInsnNode.desc.endsWith('[' + this.returnType.getDescriptor())) {
            throw new InvalidInjectionException(this.info, "Injection must take place after an invoke insn which returns an array of enums");
        }

        if (!Constants.CLINIT.equals(target.method.name)) {
            throw new InvalidInjectionException(this.info, "Injection must take place inside the <clinit> method");
        }

        InsnList injectedInsns = new InsnList();
        injectedInsns.add(new InsnNode(Opcodes.DUP)); // array array
        injectedInsns.add(new InsnNode(Opcodes.ARRAYLENGTH)); // array int
        injectedInsns.add(new InsnNode(Opcodes.ICONST_1)); // array int int
        injectedInsns.add(new InsnNode(Opcodes.IADD)); // array int
        injectedInsns.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                Type.getInternalName(Arrays.class),
                "copyOf",
                "([Ljava/lang/Object;I)[Ljava/lang/Object;",
                false)); // array
        injectedInsns.add(new TypeInsnNode(Opcodes.CHECKCAST,
                '[' + this.returnType.getDescriptor())); // array
        injectedInsns.add(new InsnNode(Opcodes.DUP)); // array array
        injectedInsns.add(new InsnNode(Opcodes.DUP)); // array array array
        injectedInsns.add(new InsnNode(Opcodes.ARRAYLENGTH)); // array array int
        injectedInsns.add(new InsnNode(Opcodes.ICONST_1)); // array array int int
        injectedInsns.add(new InsnNode(Opcodes.ISUB)); // array array int
        injectedInsns.add(new InsnNode(Opcodes.DUP)); // array array int int
        this.invokeHandler(injectedInsns); // array array int enum
        injectedInsns.add(new InsnNode(Opcodes.DUP_X2)); // array enum array int enum
        injectedInsns.add(new InsnNode(Opcodes.AASTORE)); // array enum
        injectedInsns.add(new FieldInsnNode(Opcodes.PUTSTATIC,
                this.returnType.getInternalName(),
                this.name,
                this.returnType.getDescriptor())); // array.. we're back where we started!

        target.extendStack().add(1).apply();
        target.insns.insert(node.getCurrentTarget(), injectedInsns);

        // todo do I really need this?
        target.classNode.fields.add(new FieldNode(
                Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_STATIC | Opcodes.ACC_ENUM,
                name,
                this.returnType.getDescriptor(),
                target.classNode.name + '.' + name + ':' + this.returnType.getDescriptor(),
                null
        ));
    }
}
