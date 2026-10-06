package first.zenith.client;

import first.zenith.ZenithMod;
import first.zenith.common.item.ZenithItem;
import first.zenith.common.particle.zenithParticle.ZenithParticleProvider;
import first.zenith.register.ZenithParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderItemInFrameEvent;

@EventBusSubscriber(modid = ZenithMod.MODID, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onRegisterParticleProvidersEvent(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ZenithParticleRegister.Zenith.get(), ZenithParticleProvider::new);
    }

    @SubscribeEvent
    public static void onRenderHandEvent(RenderHandEvent event) {
        if (event.getItemStack().getItem() instanceof ZenithItem) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.isUsingItem()) {
                event.setCanceled(true);
            }
        }
    }
}
