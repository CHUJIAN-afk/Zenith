package first.zenith.client;

import first.zenith.ZenithMod;
import first.zenith.common.particle.zenithParticle.ZenithParticleProvider;
import first.zenith.register.ZenithParticleRegister;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = ZenithMod.MODID, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ZenithParticleRegister.Zenith.get(), ZenithParticleProvider::new);
    }
}
