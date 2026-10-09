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

/**
 * Forge 1.20.1 主类。
 * <p>
 * 与 1.21.1 的差异：
 * <ul>
 *   <li>{@code @Mod} 与 {@code IEventBus} 来自 Forge，构造参数为 {@code FMLJavaModLoadingContext}；</li>
 *   <li>各注册类改由 PortLib 承载，构造期已挂到 mod 总线，不再需要传入 eventBus；</li>
 *   <li>{@code @EventBusSubscriber} 注解改为显式的 {@code init()} 挂载，且客户端部分由
 *       {@link PortEnvironment#isPhysicalClient()} 分流，避免专用服务端加载客户端类。</li>
 * </ul>
 * </p>
 */
@Mod(ZenithMod.MODID)
public class ZenithMod {

    public static final String MODID = "zenith";
    public static final LyraItemRegistries REGISTRIES = LyraItemRegistries.create(MODID).languageInit(registries -> registries.language("modid.zenith", "Zenith", "天顶"));

    public ZenithMod(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();
        // 物品注册仍需 eventBus：LyraItemRegistries 用它挂 GatherDataEvent（数据生成）。
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
