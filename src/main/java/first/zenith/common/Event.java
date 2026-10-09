package first.zenith.common;


import first.lyra.api.LyraAPI;
import first.zenith.common.attachment.ZenithData;
import first.zenith.common.item.ZenithItem;
import first.zenith.register.ZenithAttachmentRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.PortEventPriority;
import org.mesdag.portlib.event.client.PortRenderLevelStageEvent;
import org.mesdag.portlib.event.tick.PortPlayerTickEvent;

/**
 * 通用事件。
 * <p>
 * 1.21.1 用 {@code @EventBusSubscriber(modid = ...)} 双端注册；Forge 1.20.1 改为显式挂监听。
 * 其中掉落实体/手持动态光照依赖客户端类，拆到 {@link #initClient()}，仅在物理客户端调用，
 * 避免专用服务端加载客户端类。
 * </p>
 */
public class Event {

    public static void init() {
        PortEventHandler.addListener(PortEventPriority.LOWEST, false, PortPlayerTickEvent.Post.class, Event::tick);
    }

    public static void initClient() {
        PortEventHandler.addListener(PortEventPriority.NORMAL, false, PortRenderLevelStageEvent.class, Event::levelRender);
    }

    public static void levelRender(PortRenderLevelStageEvent event) {
        if (event.getStage() == PortRenderLevelStageEvent.Stage.AFTER_LEVEL) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientLevel level = minecraft.level;
            if (level != null) {
                Iterable<Entity> entities = level.entitiesForRendering();
                float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(true);
                for (Entity entity : entities) {
                    Vec3 pos = entity.getEyePosition(partialTick);
                    if (entity instanceof ItemEntity itemEntity && itemEntity.getItem().getItem() instanceof ZenithItem) {
                        LyraAPI.light(pos, 0.5f);
                    } else if (entity instanceof LivingEntity living) {
                        for (EquipmentSlot slot : EquipmentSlot.values()) {
                            if (living.getItemBySlot(slot).getItem() instanceof ZenithItem) {
                                LyraAPI.light(pos, 0.5f);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void tick(PortPlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ZenithData data = player.getData(ZenithAttachmentRegister.ZenithData);
        if (!player.level().isClientSide()) {
            data.tick();
        }
    }
}
