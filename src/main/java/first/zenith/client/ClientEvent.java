package first.zenith.client;

import first.lyra.api.LyraAPI;
import first.zenith.common.item.ZenithItem;
import first.zenith.common.particle.zenithParticle.ZenithParticleProvider;
import first.zenith.register.ZenithParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.client.PortRegisterParticleProvidersEvent;
import org.mesdag.portlib.event.client.PortRenderHandEvent;

public class ClientEvent {

    public static void init() {
        PortEventHandler.addListener(ClientEvent::onRegisterParticleProvidersEvent);
        PortEventHandler.addListener(ClientEvent::onRenderHandEvent);
        PortEventHandler.addListener(ClientEvent::levelRender);
    }

    public static void onRegisterParticleProvidersEvent(PortRegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ZenithParticleRegister.Zenith.get(), ZenithParticleProvider::new);
    }

    public static void onRenderHandEvent(PortRenderHandEvent event) {
        if (event.getItemStack().getItem() instanceof ZenithItem) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.isUsingItem()) {
                event.setCanceled(true);
            }
        }
    }

    public static void levelRender(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientLevel level = minecraft.level;
            if (level != null) {
                Iterable<Entity> entities = level.entitiesForRendering();
                float partialTick = event.getPartialTick();
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
}
