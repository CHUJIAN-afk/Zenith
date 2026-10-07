package first.zenith.client.attachmentEntityRenderer.minion;

import com.mojang.blaze3d.vertex.PoseStack;
import first.lyra.api.LyraAPI;
import first.lyra.client.render.AbstractAttachmentEntityRenderer;
import first.lyra.client.render.ColorBufferSource;
import first.lyra.client.render.ModelContext;
import first.lyra.client.render.RenderContext;
import first.lyra.common.attachmentEntity.PathNode;
import first.zenith.common.minion.ZenithDecoration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ZenithDecorationRenderer extends AbstractAttachmentEntityRenderer<ZenithDecoration> {

    @Override
    protected RenderContext<ZenithDecoration> createContext(ZenithDecoration entity, PathNode visualNode, float partialTick, int packedLight) {
        RenderContext<ZenithDecoration> renderContext = super.createContext(entity, visualNode, partialTick, packedLight);
        return renderContext.model(new ModelContext().alphaDistanceFactor(3).rotationOffset(0, 90, -45));
    }

    @Override
    protected void render(PoseStack poseStack, MultiBufferSource bufferSource) {
        ItemStack itemStack = context.entity.getItemStack();
        if (!itemStack.isEmpty()) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ColorBufferSource source = new ColorBufferSource(bufferSource);
            source.setColor(context.color.argbInt());
            itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, poseStack, source, context.entity.getLevel(), 0);
            LyraAPI.light(context.visualNode.pos(), 0.5f);
        }
    }
}
