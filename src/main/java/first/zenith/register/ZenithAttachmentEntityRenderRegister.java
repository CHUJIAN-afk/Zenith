package first.zenith.register;

import first.lyra.client.render.AttachmentEntityRenderDispatcher;
import first.zenith.ZenithMod;
import first.zenith.client.attachmentEntityRenderer.minion.ZenithDecorationRenderer;
import first.zenith.client.attachmentEntityRenderer.projectile.ZenithRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ZenithMod.MODID, value = Dist.CLIENT)
public class ZenithAttachmentEntityRenderRegister {

    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
        AttachmentEntityRenderDispatcher.register(ZenithAttachmentEntityRegister.ZENITH.get(), new ZenithRenderer());
        AttachmentEntityRenderDispatcher.register(ZenithAttachmentEntityRegister.ZENITH_DECORATION.get(), new ZenithDecorationRenderer());
    }
}
