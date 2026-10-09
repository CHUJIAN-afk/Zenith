package first.zenith.client;

import first.zenith.common.item.ZenithItem;
import first.zenith.common.particle.zenithParticle.ZenithParticleProvider;
import first.zenith.register.ZenithParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.client.PortRegisterParticleProvidersEvent;
import org.mesdag.portlib.event.client.PortRenderHandEvent;

/**
 * 客户端事件。
 * <p>
 * 1.21.1 用 {@code @EventBusSubscriber(Dist.CLIENT)}；Forge 1.20.1 改为在客户端由
 * {@link #init()} 显式挂监听。
 * </p>
 */
public class ClientEvent {

    public static void init() {
        PortEventHandler.addListener(ClientEvent::onRegisterParticleProvidersEvent);
        PortEventHandler.addListener(ClientEvent::onRenderHandEvent);
    }

    public static void onRegisterParticleProvidersEvent(PortRegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ZenithParticleRegister.Zenith.get(), ZenithParticleProvider::new);
    }

    public static void onRenderHandEvent(PortRenderHandEvent event) {
        if (event.getItemStack().getItem() instanceof ZenithItem) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.isUsingItem()) {
                event.setCanceled(true);
            }
        }
    }
}
