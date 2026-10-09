package first.zenith.register;

import first.zenith.ZenithMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import org.mesdag.portlib.registries.PortRegisterHandler;
import org.mesdag.portlib.registries.PortRegistration;
import org.mesdag.portlib.registries.PortRegistryEntry;


public class ZenithCreativeTabRegister {

    private static final PortRegistration<CreativeModeTab> Register = PortRegisterHandler.create(ZenithMod.MODID, Registries.CREATIVE_MODE_TAB);
    private static final PortRegistryEntry<CreativeModeTab, CreativeModeTab> Tab = Register.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("modid.zenith"))
            .icon(() -> ZenithItemRegister.Zenith.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ZenithItemRegister.Zenith.get());
                output.accept(ZenithItemRegister.TrueCopperShortsword.get());
            })
            .build());

    public static void register() {
    }
}
