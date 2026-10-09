package first.zenith.register;

import first.lyra.common.attachmentEntity.AttachmentEntity;
import first.lyra.common.attachmentEntity.AttachmentEntityType;
import first.lyra.register.LyraRegistries;
import first.zenith.common.minion.ZenithDecoration;
import first.zenith.common.projectile.Zenith;
import net.minecraft.core.Holder;
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

    /**
     * Lyra 的 {@code AttachmentEntity} 构造函数要求 {@code Holder<AttachmentEntityType<?>>}，
     * 而 Lyra 自定义注册表的泛型是 {@code AttachmentEntityType<? extends AttachmentEntity>}。
     * 二者只是类型参数写法不同（{@code ? extends AttachmentEntity} 与 {@code ?}），
     * 因泛型不变性无法直接赋值，这里做一次受检的窄化转换；运行时类型完全相同，无行为影响。
     */
    @SuppressWarnings("unchecked")
    public static <T extends AttachmentEntity> Holder<AttachmentEntityType<?>> holder(PortRegistryEntry<AttachmentEntityType<? extends AttachmentEntity>, AttachmentEntityType<T>> entry) {
        return (Holder<AttachmentEntityType<?>>) (Holder<?>) entry;
    }

    /** Lyra 的自定义注册表由 Lyra 自身挂载，无需在此重复注册。 */
    public static void register() {
    }
}
