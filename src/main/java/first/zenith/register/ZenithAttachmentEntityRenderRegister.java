package first.zenith.register;

import first.lyra.client.render.AttachmentEntityRenderDispatcher;
import first.zenith.client.attachmentEntityRenderer.minion.ZenithDecorationRenderer;
import first.zenith.client.attachmentEntityRenderer.projectile.ZenithRenderer;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.lifecycle.PortFMLClientSetupEventPort;

/**
 * 飞剑与背显渲染器注册。
 * <p>
 * 1.21.1 用 {@code @EventBusSubscriber(Dist.CLIENT)}；Forge 1.20.1 改为在客户端由
 * {@link #init()} 显式挂监听。
 * </p>
 * <p>
 * 注意类名：PortLib 1.2.4（Lyra-1201 所依赖的版本）中该类为 {@code PortFMLClientSetupEventPort}，
 * 本地 1.2.7 源码才改名为 {@code PortFMLClientSetupEvent}。此处按 1.2.4 的制品名引用。
 * </p>
 */
public class ZenithAttachmentEntityRenderRegister {

    public static void init() {
        PortEventHandler.addListener(ZenithAttachmentEntityRenderRegister::onClientSetup);
    }

    public static void onClientSetup(PortFMLClientSetupEventPort event) {
        // 该事件为并行分发，渲染器表是共享静态 Map，交由主线程排队执行。
        event.enqueueWork(() -> {
            AttachmentEntityRenderDispatcher.register(ZenithAttachmentEntityRegister.ZENITH.get(), new ZenithRenderer());
            AttachmentEntityRenderDispatcher.register(ZenithAttachmentEntityRegister.ZENITH_DECORATION.get(), new ZenithDecorationRenderer());
        });
    }
}
