package first.zenith.register;

import first.zenith.ZenithMod;
import first.zenith.common.attachment.ZenithData;
import org.mesdag.portlib.attachment.PortAttachmentType;
import org.mesdag.portlib.registries.PortAttachmentRegistration;
import org.mesdag.portlib.registries.PortRegisterHandler;
import org.mesdag.portlib.registries.PortRegistryEntry;

public class ZenithAttachmentRegister {

    private static final PortAttachmentRegistration Register = PortRegisterHandler.attachment(ZenithMod.MODID);

    public static final PortRegistryEntry<PortAttachmentType<?>, PortAttachmentType<ZenithData>> ZenithData =
            Register.registerSimple("zenith_data", () -> PortAttachmentType.builder(ZenithData::new));

    /** PortLib 的注册在构造期已挂到 mod 总线，无需显式注册。 */
    public static void register() {
    }
}
