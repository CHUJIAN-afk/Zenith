package first.zenith.register;

import first.zenith.ZenithMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import org.mesdag.portlib.registries.PortRegisterHandler;
import org.mesdag.portlib.registries.PortRegistration;
import org.mesdag.portlib.registries.PortRegistryEntry;

public class ZenithSoundRegister {

    private static final PortRegistration<SoundEvent> Register = PortRegisterHandler.create(ZenithMod.MODID, Registries.SOUND_EVENT);

    public static final PortRegistryEntry<SoundEvent, SoundEvent> Zenith = create("zenith");

    private static PortRegistryEntry<SoundEvent, SoundEvent> create(String name) {
        return Register.register(name, () -> SoundEvent.createVariableRangeEvent(ZenithMod.rl(name)));
    }

    public static void register() {
    }
}
