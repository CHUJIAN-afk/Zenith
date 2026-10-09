package first.zenith.utils;

import first.lyra.utils.LyraStreamCodecs;
import first.zenith.common.projectile.Zenith;
import org.mesdag.portlib.network.PortRegistryFriendlyByteBuf;
import org.mesdag.portlib.network.codec.PortByteBufCodecs;
import org.mesdag.portlib.network.codec.PortStreamCodec;

public interface ZenithStreamCodecs extends LyraStreamCodecs {
    PortStreamCodec<PortRegistryFriendlyByteBuf, Zenith.RenderType> ZENITH_RENDER_TYPE = PortStreamCodec.composite(PortByteBufCodecs.STRING_UTF8, Enum::name, Zenith.RenderType::valueOf);
}
