package first.zenith.utils;

import first.lyra.utils.LyraStreamCodecs;
import first.zenith.common.projectile.Zenith;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface ZenithStreamCodecs extends LyraStreamCodecs {
    StreamCodec<RegistryFriendlyByteBuf, Zenith.RenderType> ZENITH_RENDER_TYPE = StreamCodec.composite(LyraStreamCodecs.STRING_UTF8, Enum::name, Zenith.RenderType::valueOf);
}
