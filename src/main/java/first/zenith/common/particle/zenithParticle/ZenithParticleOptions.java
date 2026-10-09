package first.zenith.common.particle.zenithParticle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import first.zenith.register.ZenithParticleRegister;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.mesdag.portlib.network.PortRegistryFriendlyByteBuf;
import org.mesdag.portlib.network.codec.PortStreamCodec;
import org.mesdag.portlib.wrapper.common.extensions.IPortFriendlyByteBufExtension;

public record ZenithParticleOptions(int color, int lifetime, float friction, float scale) implements ParticleOptions {

    public static final MapCodec<ZenithParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("color").forGetter(ZenithParticleOptions::color),
                    Codec.INT.fieldOf("lifetime").forGetter(ZenithParticleOptions::lifetime),
                    Codec.FLOAT.fieldOf("friction").forGetter(ZenithParticleOptions::friction),
                    Codec.FLOAT.fieldOf("scale").forGetter(ZenithParticleOptions::scale)
            ).apply(instance, ZenithParticleOptions::new)
    );

    public static final PortStreamCodec<PortRegistryFriendlyByteBuf, ZenithParticleOptions> STREAM_CODEC = PortStreamCodec.ofMember(
            (options, buffer) -> {
                buffer.writeInt(options.color);
                buffer.writeInt(options.lifetime);
                buffer.writeFloat(options.friction);
                buffer.writeFloat(options.scale);
            },
            buffer -> new ZenithParticleOptions(
                    buffer.readInt(),
                    buffer.readInt(),
                    buffer.readFloat(),
                    buffer.readFloat()
            )
    );

    @Override
    public @NotNull ParticleType<ZenithParticleOptions> getType() {
        return ZenithParticleRegister.Zenith.get();
    }

    @Override
    public void writeToNetwork(@NotNull FriendlyByteBuf buffer) {
        STREAM_CODEC.encode(IPortFriendlyByteBufExtension.of(buffer).wrap(), this);
    }

    @Override
    public @NotNull String writeToString() {
        return CODEC.codec().encodeStart(NbtOps.INSTANCE, this)
                .result().map(Object::toString)
                .orElse("");
    }
}
