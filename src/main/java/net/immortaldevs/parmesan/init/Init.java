package net.immortaldevs.parmesan.init;

import net.immortaldevs.parmesan.injection.struct.GrabOperandInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

public final class Init {
    private static boolean initialised = false;

    public static void init() {
        if (initialised) return;
        initialised = true;

        InjectionInfo.register(GrabOperandInjectionInfo.class);
    }
}
