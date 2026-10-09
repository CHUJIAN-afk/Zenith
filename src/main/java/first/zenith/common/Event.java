package first.zenith.common;


import first.zenith.common.attachment.ZenithData;
import first.zenith.register.ZenithAttachmentRegister;
import net.minecraft.world.entity.player.Player;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.tick.PortPlayerTickEvent;

public class Event {

    public static void init() {
        PortEventHandler.addListener(Event::tick);
    }

    public static void tick(PortPlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ZenithData data = player.getData(ZenithAttachmentRegister.ZenithData);
        if (!player.level().isClientSide()) {
            data.tick();
        }
    }
}
