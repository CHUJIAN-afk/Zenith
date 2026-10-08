package first.zenith.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLLoader;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class RenderUtil {

    public static float getPartialTick() {
        if (FMLLoader.getDist().isClient()) {
            return Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
        }
        return 1;
    }
}
