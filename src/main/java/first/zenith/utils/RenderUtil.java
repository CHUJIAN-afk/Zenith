package first.zenith.utils;

import org.mesdag.portlib.client.PortDeltaTicker;
import org.mesdag.portlib.wrapper.PortEnvironment;

public class RenderUtil {

    /**
     * 1.21.1 用 {@code Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true)}；
     * 1.20.1 由 PortLib 的 {@link PortDeltaTicker} 提供等价实现。
     */
    public static float getPartialTick() {
        if (PortEnvironment.isPhysicalClient()) {
            return PortDeltaTicker.INSTANCE.getGameTimeDeltaPartialTick(true);
        }
        return 1;
    }
}
