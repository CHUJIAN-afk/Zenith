package first.zenith.register;

import first.lyra.client.render.AttachmentEntityRenderDispatcher;
import first.lyra.client.render.IAttachmentEntityRenderer;
import first.lyra.common.attachmentEntity.AttachmentEntity;
import first.lyra.common.attachmentEntity.AttachmentEntityType;
import first.zenith.ZenithMod;
import first.zenith.client.attachmentEntityRenderer.projectile.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

/**
 * 附件实体渲染器注册类（仅客户端加载）。
 * <p>
 * 渲染器引用客户端类（LocalPlayer 等），必须与实体类型注册分离：
 * 若由 {@link ZenithAttachmentEntityRegister} 直接引用，
 * 服务端加载该类时会被 RuntimeDistCleaner 拦截
 * （Attempted to load class ... for invalid dist DEDICATED_SERVER）导致模组崩溃。
 * 本类通过 {@code @EventBusSubscriber(value = Dist.CLIENT)} 保证只在客户端加载执行。
 */
@EventBusSubscriber(modid = ZenithMod.MODID, value = Dist.CLIENT)
public class ZenithAttachmentEntityRenderRegister {

    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {

        // ===================== 射弹 =====================
        register(ZenithAttachmentEntityRegister.ZENITH, ZenithRenderer::new);
    }

    private static <T extends AttachmentEntity> void register(DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<T>> type, Supplier<IAttachmentEntityRenderer<T>> renderer) {
        AttachmentEntityRenderDispatcher.register(type.get(), renderer.get());
    }
}
