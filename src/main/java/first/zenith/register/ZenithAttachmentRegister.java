package first.zenith.register;

import first.zenith.ZenithMod;
import first.zenith.common.attachment.ZenithData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ZenithAttachmentRegister {

    private static final DeferredRegister<AttachmentType<?>> Register = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ZenithMod.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ZenithData>> ZenithData =
            Register.register("zenith_data", () -> AttachmentType.builder(ZenithData::new)
                    .build());

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }
}
