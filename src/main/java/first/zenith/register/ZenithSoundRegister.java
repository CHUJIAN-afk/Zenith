package first.zenith.register;

import first.zenith.ZenithMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ZenithSoundRegister {

    private static final DeferredRegister<SoundEvent> Register = DeferredRegister.create(Registries.SOUND_EVENT, ZenithMod.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> Zenith = create("zenith");

    private static DeferredHolder<SoundEvent, SoundEvent> create(String name) {
        return Register.register(name, () -> SoundEvent.createVariableRangeEvent(ZenithMod.rl(name)));
    }

    public static void register(IEventBus bus) {
        Register.register(bus);
    }

}
