package first.zenith.client.attachmentEntityRenderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import first.lyra.client.render.RenderContext;
import first.lyra.client.render.trail.RibbonTrailContext;
import first.lyra.client.render.trail.TrailContext;
import first.lyra.common.attachmentEntity.PathNode;
import first.zenith.ZenithMod;
import first.zenith.common.projectile.Zenith;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ZenithTrailContext extends RibbonTrailContext<Zenith> {

    @Override
    protected List<InterpolatedNode> buildSmoothNodes(Zenith entity, PathNode visualNode, float partialTick) {
        List<InterpolatedNode> result = new ArrayList<>();
        float tick = entity.getTickCount() + partialTick;
        int count = 60;
        float step = 0.05f * Math.min(1.0f, Math.min(tick, 11.0f - tick) / 4.0f);
        if (step > 0) {
            for (int i = 0; i < count; i++) {
                PathNode node = entity.getRenderNodeFromTickCount(tick - i * step);
                result.add(new InterpolatedNode(node.pos(), node.toQuaternion()));
            }
        }
        return result;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, RenderContext<Zenith> context) {
        List<TrailContext.InterpolatedNode> nodes = this.buildSmoothNodes(context.entity, context.visualNode, context.partialTick);
        int nodeCount = nodes.size();
        if (nodeCount > 1) {
            VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityTranslucent(ZenithMod.rl("textures/zenith_trail.png")));
            PoseStack.Pose pose = poseStack.last();
            Vec3 renderPos = context.visualNode.pos();
            Vector3f currTip = new Vector3f();
            Vector3f currBase = new Vector3f();
            Vector3f prevTip = new Vector3f();
            Vector3f prevBase = new Vector3f();

            for(int i = 0; i < nodeCount - 1; ++i) {
                TrailContext.InterpolatedNode curr = nodes.get(i);
                TrailContext.InterpolatedNode prev = nodes.get(i + 1);
                float currProgress = (float)i / (float)(nodeCount - 1);
                float prevProgress = (float)(i + 1) / (float)(nodeCount - 1);
                currTip.set(0.0F, 0.0F, this.upOffset).rotate(curr.rot());
                currBase.set(0.0F, 0.0F, this.downOffset).rotate(curr.rot());
                prevTip.set(0.0F, 0.0F, this.upOffset).rotate(prev.rot());
                prevBase.set(0.0F, 0.0F, this.downOffset).rotate(prev.rot());
                int currColorRGB = this.colorFunction.getColor(context.entity, currProgress, context.partialTick);
                int prevColorRGB = this.colorFunction.getColor(context.entity, prevProgress, context.partialTick);
                float currBright = this.tipBrightnessBoost.getBoost(context.entity, currProgress);
                float prevBright = this.tipBrightnessBoost.getBoost(context.entity, prevProgress);
                float currAlphaBoost = this.tipAlphaBoost.getBoost(context.entity, currProgress);
                float prevAlphaBoost = this.tipAlphaBoost.getBoost(context.entity, prevProgress);
                int currTipColor = packColor(currColorRGB, Math.max(0.0F, 1.0F - currProgress) * 0.1F * currAlphaBoost, currBright);
                int currBaseColor = packColor(currColorRGB, Math.max(0.0F, 1.0F - currProgress * 2.5F) * 0.04F * currAlphaBoost, currBright);
                int prevTipColor = packColor(prevColorRGB, Math.max(0.0F, 1.0F - prevProgress) * 0.1F * prevAlphaBoost, prevBright);
                int prevBaseColor = packColor(prevColorRGB, Math.max(0.0F, 1.0F - prevProgress * 2.5F) * 0.04F * prevAlphaBoost, prevBright);
                float crx = (float)(curr.pos().x - renderPos.x);
                float cry = (float)(curr.pos().y - renderPos.y);
                float crz = (float)(curr.pos().z - renderPos.z);
                float prx = (float)(prev.pos().x - renderPos.x);
                float pry = (float)(prev.pos().y - renderPos.y);
                float prz = (float)(prev.pos().z - renderPos.z);
                buffer.addVertex(pose, crx + currTip.x, cry + currTip.y, crz + currTip.z).setColor(currTipColor).setUv(0.0F, currProgress).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0.0F, 0.0F, 1.0F);
                buffer.addVertex(pose, crx + currBase.x, cry + currBase.y, crz + currBase.z).setColor(currBaseColor).setUv(1.0F, currProgress).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0.0F, 0.0F, 1.0F);
                buffer.addVertex(pose, prx + prevBase.x, pry + prevBase.y, prz + prevBase.z).setColor(prevBaseColor).setUv(1.0F, prevProgress).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0.0F, 0.0F, 1.0F);
                buffer.addVertex(pose, prx + prevTip.x, pry + prevTip.y, prz + prevTip.z).setColor(prevTipColor).setUv(0.0F, prevProgress).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0.0F, 0.0F, 1.0F);
            }
        }
    }
}
