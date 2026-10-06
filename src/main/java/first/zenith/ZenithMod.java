package first.zenith;

import first.lyra.register.LyraItemRegistries;
import first.zenith.register.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ZenithMod.MODID)
public class ZenithMod {

    public static final String MODID = "zenith";
    public static final LyraItemRegistries REGISTRIES = LyraItemRegistries.create(MODID).languageInit(registries -> registries.language("modid.zenith", "Zenith", "天顶"));

    public ZenithMod(IEventBus eventBus) {
        REGISTRIES.register(eventBus, ZenithItemRegister::register);
        ZenithCreativeTabRegister.register(eventBus);
        ZenithAttachmentEntityRegister.register(eventBus);
        ZenithSoundRegister.register(eventBus);
        ZenithAttachmentRegister.register(eventBus);
        ZenithParticleRegister.register(eventBus);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path.toLowerCase());
    }
}
