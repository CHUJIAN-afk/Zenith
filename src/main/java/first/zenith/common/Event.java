package first.zenith.common;


import first.lyra.api.LyraAPI;
import first.zenith.ZenithMod;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = ZenithMod.MODID)
public class Event {

    @SubscribeEvent
    public static void levelRender(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
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

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ZenithData data = player.getData(ZenithAttachmentRegister.ZenithData);
        if (!player.level().isClientSide()) {
            data.tick();
        }
    }
}

