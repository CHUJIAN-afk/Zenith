package first.zenith.common.minion;

import first.lyra.common.attachmentEntity.AttachmentEntityType;
import first.lyra.common.attachmentEntity.PathNode;
import first.lyra.common.attachmentEntity.SyncFieldDispatcher;
import first.lyra.common.minion.Minion;
import first.lyra.common.minion.MinionSlotType;
import first.zenith.register.ZenithAttachmentEntityRegister;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class ZenithDecoration extends Minion {

    private ItemStack itemStack = ItemStack.EMPTY;

    public ZenithDecoration() {
        super(ZenithAttachmentEntityRegister.holder(ZenithAttachmentEntityRegister.ZENITH_DECORATION));
        setSlotCost(0);
    }

    @Override
    protected void registerSyncFields(SyncFieldDispatcher fields) {
        super.registerSyncFields(fields);
        fields.field(ItemStack.STREAM_CODEC, () -> itemStack, value -> itemStack = value);
    }

    @Override
    public void tick() {
        super.tick();
        PathNode renderNode = getRenderNode(1);
        PathNode idlePathNode = getIdlePathNode(1);
        float partialTick = (float) (0.15f + Math.min(0.6, Math.abs(renderNode.pos().y() - idlePathNode.pos().y()) * 0.2f));
        PathNode node = idlePathNode.modifyPos(new Vec3(idlePathNode.pos().x(), renderNode.lerp(idlePathNode, partialTick).pos().y(), idlePathNode.pos().z()));
        setCurrentPathNode(node);
    }

    @Override
    public PathNode getRenderNode(float partialTick) {
        PathNode renderNode = super.getRenderNode(partialTick);
        PathNode idlePathNode = getIdlePathNode(partialTick);
        PathNode pathNode = renderNode.lerp(idlePathNode, 0.5f);
        return idlePathNode.modifyPos(new Vec3(pathNode.pos().x(), renderNode.pos().y(), pathNode.pos().z()));
    }

    public PathNode getIdlePathNode(float partialTick) {
        float bodyYaw = Mth.rotLerp(partialTick, owner.yBodyRotO, owner.yBodyRot);
        float headYaw = Mth.rotLerp(partialTick, owner.yHeadRotO, owner.yHeadRot);
        float playerYaw = Mth.wrapDegrees(bodyYaw + Mth.wrapDegrees(headYaw - bodyYaw) * 0.5f);
        float rad = (float) Math.toRadians(-playerYaw + 180);
        float backX = (float) Math.sin(rad);
        float backZ = (float) Math.cos(rad);
        float rightX = (float) Math.cos(rad);
        float rightZ = (float) -Math.sin(rad);
        int order = getOrder();
        double localZ = 0.75 + order * 0.12;
        double floatSpeed = 0.08 + order * 0.01;
        double floatAngle = (owner.tickCount + partialTick) * floatSpeed + order * 1.33;
        Vec3 playerPos = owner.getPosition(partialTick);
        Vec3 targetPos = playerPos.add(localZ * backX + Math.cos(floatAngle) * 0.075 * rightX, owner.getBbHeight() * 0.6 + Math.sin(floatAngle) * 0.075, localZ * backZ + Math.cos(floatAngle) * 0.075 * rightZ);
        return new PathNode(targetPos, playerYaw - 90, 75 - order * 5f, 100);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public boolean isAlive() {
        return !itemStack.isEmpty();
    }

    @Override
    public MinionSlotType getSlotType() {
        return MinionSlotType.None;
    }

    @Override
    public int getSearchDistance() {
        return 0;
    }
}
