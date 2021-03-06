package net.immortaldevs.divineintervention.init;

import net.immortaldevs.divineintervention.injection.struct.CustomInjectInjectionInfo;
import net.immortaldevs.divineintervention.injection.struct.EnumInjectInjectionInfo;
import net.immortaldevs.divineintervention.injection.struct.ModifyOperandInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

public final class Init {
    private static boolean initialised = false;

    public static void init() {
        if (initialised) return;
        initialised = true;

        InjectionInfo.register(CustomInjectInjectionInfo.class);
        InjectionInfo.register(ModifyOperandInjectionInfo.class);
        InjectionInfo.register(EnumInjectInjectionInfo.class);
    }
}
