package first.zenith.register;

import first.lyra.client.render.AttachmentEntityRenderDispatcher;
import first.zenith.client.attachmentEntityRenderer.minion.ZenithDecorationRenderer;
import first.zenith.client.attachmentEntityRenderer.projectile.ZenithRenderer;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.lifecycle.PortFMLClientSetupEventPort;

public class ZenithAttachmentEntityRenderRegister {

    public static void init() {
        PortEventHandler.addListener(ZenithAttachmentEntityRenderRegister::onClientSetup);
    }

    public static void onClientSetup(PortFMLClientSetupEventPort event) {
        AttachmentEntityRenderDispatcher.register(ZenithAttachmentEntityRegister.ZENITH.get(), new ZenithRenderer());
        AttachmentEntityRenderDispatcher.register(ZenithAttachmentEntityRegister.ZENITH_DECORATION.get(), new ZenithDecorationRenderer());
    }
}
