package first.zenith.register;

import first.lyra.common.attachmentEntity.AttachmentEntity;
import first.lyra.common.attachmentEntity.AttachmentEntityType;
import first.lyra.register.LyraRegistries;
import first.zenith.ZenithMod;
import first.zenith.common.projectile.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * 附件实体类型注册类。
 * <p>
 * 只注册实体类型本身；渲染器注册见 {@link ZenithAttachmentEntityRenderRegister}（仅客户端加载）。
 * 渲染器引用客户端类，绝不能在本类中直接引用，否则服务端加载本类时会被
 * RuntimeDistCleaner 拦截（Attempted to load class ... for invalid dist DEDICATED_SERVER）导致崩溃。
 */
public class ZenithAttachmentEntityRegister {

    private static final DeferredRegister<AttachmentEntityType<?>> Register = DeferredRegister.create(LyraRegistries.ATTACHMENT_ENTITY_TYPES, ZenithMod.MODID);

    // ===================== 射弹类型 =====================

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<Zenith>> ZENITH = register("zenith", Zenith::new);

    private static <T extends AttachmentEntity> DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<T>> register(String name, Supplier<T> supplier) {
        return Register.register(name, ResourceLocation -> new AttachmentEntityType<>(ResourceLocation, supplier));
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }
}
