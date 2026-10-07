package first.zenith.register;

import first.lyra.common.attachmentEntity.AttachmentEntity;
import first.lyra.common.attachmentEntity.AttachmentEntityType;
import first.lyra.register.LyraRegistries;
import first.zenith.ZenithMod;
import first.zenith.common.minion.ZenithDecoration;
import first.zenith.common.projectile.Zenith;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ZenithAttachmentEntityRegister {

    private static final DeferredRegister<AttachmentEntityType<?>> Register = DeferredRegister.create(LyraRegistries.ATTACHMENT_ENTITY_TYPES, ZenithMod.MODID);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<Zenith>> ZENITH = register("zenith", Zenith::new);

    public static final DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<ZenithDecoration>> ZENITH_DECORATION = register("zenith_decoration", ZenithDecoration::new);

    private static <T extends AttachmentEntity> DeferredHolder<AttachmentEntityType<?>, AttachmentEntityType<T>> register(String name, Supplier<T> supplier) {
        return Register.register(name, ResourceLocation -> new AttachmentEntityType<>(ResourceLocation, supplier));
    }

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }
}
