package first.zenith.register;

import first.zenith.ZenithMod;
import first.zenith.common.particle.zenithParticle.ZenithParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.mesdag.portlib.registries.PortParticleTypeRegistration;
import org.mesdag.portlib.registries.PortRegisterHandler;
import org.mesdag.portlib.registries.PortRegistryEntry;

public class ZenithParticleRegister {

    private static final PortParticleTypeRegistration Register = PortRegisterHandler.particleType(ZenithMod.MODID);

    /** PortLib 用 MapCodec + PortStreamCodec 直接构建 1.21 形态的 ParticleType。 */
    public static final PortRegistryEntry<ParticleType<?>, ParticleType<ZenithParticleOptions>> Zenith =
            Register.register("zenith", false, ZenithParticleOptions.CODEC, ZenithParticleOptions.STREAM_CODEC);

    public static void register() {
    }
}
