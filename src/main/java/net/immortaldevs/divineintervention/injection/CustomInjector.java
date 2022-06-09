package net.immortaldevs.divineintervention.injection;

import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

public abstract class CustomInjector extends Injector {
    public CustomInjector(InjectionInfo info) {
        super(info, "@CustomInject");
    }
}
