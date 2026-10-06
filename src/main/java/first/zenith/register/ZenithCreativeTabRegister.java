package first.zenith.register;

import first.zenith.ZenithMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ZenithCreativeTabRegister {

    private static final DeferredRegister<CreativeModeTab> Register = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ZenithMod.MODID);
    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> Tab = Register.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("modid.zenith"))
            .icon(() -> ZenithItemRegister.Zenith.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ZenithItemRegister.Zenith.get());
                output.accept(ZenithItemRegister.TrueCopperShortsword.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        Register.register(eventBus);
    }
}
