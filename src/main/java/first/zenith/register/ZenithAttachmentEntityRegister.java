package first.zenith.register;

import PortLib.extensions.net.minecraftforge.registries.DeferredRegister.PortDeferredRegisterExtension;
import first.lyra.common.attachmentEntity.AttachmentEntity;
import first.lyra.common.attachmentEntity.AttachmentEntityType;
import first.lyra.register.LyraRegistries;
import first.zenith.ZenithMod;
import first.zenith.common.minion.ZenithDecoration;
import first.zenith.common.projectile.Zenith;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ZenithAttachmentEntityRegister {

    public static final DeferredRegister<AttachmentEntityType<? extends AttachmentEntity>> TYPES = DeferredRegister.create(LyraRegistries.ATTACHMENT_ENTITY_TYPES.key(), ZenithMod.MODID);

    public static final RegistryObject<AttachmentEntityType<Zenith>> ZENITH = register("zenith", Zenith::new);

    public static final RegistryObject<AttachmentEntityType<ZenithDecoration>> ZENITH_DECORATION = register("zenith_decoration", ZenithDecoration::new);

    private static <T extends AttachmentEntity> RegistryObject<AttachmentEntityType<T>> register(String name, Supplier<T> supplier) {
        return PortDeferredRegisterExtension.register(TYPES, name, id -> new AttachmentEntityType<>(id, supplier));
    }

    public static void register(IEventBus eventBus) {
        TYPES.register(eventBus);
    }
}
