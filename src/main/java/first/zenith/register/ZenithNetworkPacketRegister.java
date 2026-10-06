package first.zenith.register;

import first.zenith.ZenithMod;
import first.zenith.network.ZenithPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;

@EventBusSubscriber(modid = ZenithMod.MODID)
public class ZenithNetworkPacketRegister {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar(ZenithMod.MODID)
                .executesOn(HandlerThread.MAIN)
                .playToServer(ZenithPacket.TYPE, ZenithPacket.STREAM_CODEC, ZenithPacket::handleServer);
    }
}
