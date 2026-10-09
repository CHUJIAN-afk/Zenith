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

/**
 * 天顶剑粒子选项：颜色（RGB）、寿命、阻力、大小由服务端下发，速度随生成时的速度传入。
 */
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

    /**
     * 1.21 已从 {@code ParticleOptions} 移除该方法；1.20.1 仍需实现。
     * 与 PortLib 的 {@code PortLibParticleType.fromNetwork} 对称：把普通缓冲区包成
     * {@code PortRegistryFriendlyByteBuf} 后交给同一个 STREAM_CODEC，保证收发格式一致。
     */
    @Override
    public void writeToNetwork(@NotNull FriendlyByteBuf buffer) {
        STREAM_CODEC.encode(IPortFriendlyByteBufExtension.of(buffer).wrap(), this);
    }

    /**
     * 1.21 已从 {@code ParticleOptions} 移除该方法；1.20.1 仍需实现，用于命令提示。
     * 与 PortLib 的 {@code PortLibParticleType.writeToString} 一致，按 CODEC 编码输出。
     */
    @Override
    public @NotNull String writeToString() {
        return CODEC.codec().encodeStart(NbtOps.INSTANCE, this)
                .result().map(Object::toString)
                .orElse("");
    }
}
