package first.zenith.common;


import first.zenith.ZenithMod;
import first.zenith.common.attachment.ZenithData;
import first.zenith.register.ZenithAttachmentRegister;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = ZenithMod.MODID)
public class Event {

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ZenithData data = player.getData(ZenithAttachmentRegister.ZenithData);
        if (!player.level().isClientSide()) {
            data.tick();
        }
    }
}
