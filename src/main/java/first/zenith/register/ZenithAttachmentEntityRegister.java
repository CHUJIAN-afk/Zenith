package first.zenith.register;

import first.lyra.common.attachmentEntity.AttachmentEntity;
import first.lyra.common.attachmentEntity.AttachmentEntityType;
import first.lyra.register.LyraRegistries;
import first.zenith.common.minion.ZenithDecoration;
import first.zenith.common.projectile.Zenith;
import org.mesdag.portlib.registries.PortRegistryEntry;

import java.util.function.Supplier;

public class ZenithAttachmentEntityRegister {

    public static final PortRegistryEntry<AttachmentEntityType<? extends AttachmentEntity>, AttachmentEntityType<Zenith>> ZENITH =
            register("zenith", Zenith::new);

    public static final PortRegistryEntry<AttachmentEntityType<? extends AttachmentEntity>, AttachmentEntityType<ZenithDecoration>> ZENITH_DECORATION =
            register("zenith_decoration", ZenithDecoration::new);

    private static <T extends AttachmentEntity> PortRegistryEntry<AttachmentEntityType<? extends AttachmentEntity>, AttachmentEntityType<T>> register(String name, Supplier<T> supplier) {
        return LyraRegistries.ATTACHMENT_ENTITY_TYPES.register(name, location -> new AttachmentEntityType<>(location, supplier));
    }

    /** Lyra 的自定义注册表由 Lyra 自身挂载，无需在此重复注册。 */
    public static void register() {
    }
}
