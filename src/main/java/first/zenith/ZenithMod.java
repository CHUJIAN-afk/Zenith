package first.zenith;

import first.lyra.register.LyraItemRegistries;
import first.zenith.client.ClientEvent;
import first.zenith.common.Event;
import first.zenith.register.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mesdag.portlib.wrapper.PortEnvironment;

@Mod(ZenithMod.MODID)
public class ZenithMod {

    public static final String MODID = "zenith";
    public static final LyraItemRegistries REGISTRIES = LyraItemRegistries.create(MODID).languageInit(registries -> registries.language("modid.zenith", "Zenith", "天顶"));

    public ZenithMod(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();
        REGISTRIES.register(eventBus, ZenithItemRegister::register);
        ZenithCreativeTabRegister.register();
        ZenithAttachmentEntityRegister.register();
        ZenithSoundRegister.register();
        ZenithAttachmentRegister.register();
        ZenithParticleRegister.register();

        Event.init();
        if (PortEnvironment.isPhysicalClient()) {
            ZenithModelRegister.init();
            ZenithAttachmentEntityRenderRegister.init();
            ClientEvent.init();
            Event.initClient();
        }
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path.toLowerCase());
    }
}
