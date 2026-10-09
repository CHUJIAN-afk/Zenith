package first.zenith.utils;

import org.mesdag.portlib.client.PortDeltaTicker;
import org.mesdag.portlib.wrapper.PortEnvironment;

public class RenderUtil {

    public static float getPartialTick() {
        if (PortEnvironment.isPhysicalClient()) {
            return PortDeltaTicker.INSTANCE.getGameTimeDeltaPartialTick(true);
        }
        return 1;
    }
}
