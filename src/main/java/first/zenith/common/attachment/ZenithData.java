package first.zenith.common.attachment;

import first.lyra.api.LyraHelper;
import first.lyra.common.attachment.TargetCache;
import first.lyra.common.attachmentEntity.PathNode;
import first.lyra.common.sound.Playable;
import first.lyra.register.LyraAttachmentRegister;
import first.zenith.common.item.ZenithItem;
import first.zenith.common.minion.ZenithDecoration;
import first.zenith.common.projectile.Zenith;
import first.zenith.mixin.AttachmentEntityAccessor;
import first.zenith.register.ZenithAttachmentEntityRegister;
import first.zenith.register.ZenithItemRegister;
import first.zenith.register.ZenithSoundRegister;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.mesdag.portlib.attachment.IPortAttachmentHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ZenithData {

    private final Player owner;
    private float power = 0;

    public ZenithData(IPortAttachmentHolder holder) {
        if (holder instanceof Player player) {
            this.owner = player;
        } else {
            throw new IllegalArgumentException("ZenithData can only be used with an Player");
        }
    }

    public void tick() {
        ItemStack mainHandItem = owner.getMainHandItem();
        ItemStack offhandItem = owner.getOffhandItem();
        Inventory inventory = owner.getInventory();
        List<ItemStack> zenithItemStackList = new ArrayList<>();
        int size = inventory.getContainerSize();
        for (int i = 0; i < size; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem() instanceof ZenithItem) {
                zenithItemStackList.add(itemStack);
                if (ItemStack.isSameItemSameTags(itemStack, mainHandItem)) {
                    zenithItemStackList.remove(zenithItemStackList.size() - 1);
                    mainHandItem = ItemStack.EMPTY;
                } else if (ItemStack.isSameItemSameTags(itemStack, offhandItem)) {
                    zenithItemStackList.remove(zenithItemStackList.size() - 1);
                    offhandItem = ItemStack.EMPTY;
                }
            }
        }
        LyraHelper helper = LyraHelper.get(owner);
        List<ZenithDecoration> list = helper.getEntityData().get(ZenithAttachmentEntityRegister.ZENITH_DECORATION.get());
        helper.remove(ZenithAttachmentEntityRegister.ZENITH_DECORATION.get());
        int count = 0;
        for (ItemStack itemStack : zenithItemStackList) {
            boolean find = false;
            for (ZenithDecoration decoration : list) {
                if (decoration.isRemove()) {
                    if (ItemStack.isSameItemSameTags(decoration.getItemStack(), itemStack)) {
                        ((AttachmentEntityAccessor) decoration).setRemove(false);
                        decoration.setOrder(count);
                        find = true;
                        break;
                    }
                }
            }
            if (!find) {
                ZenithDecoration zenithDecoration = new ZenithDecoration();
                zenithDecoration.setItemStack(itemStack);
                zenithDecoration.setOwner(owner);
                zenithDecoration.setOrder(count);
                zenithDecoration.init(zenithDecoration.getIdlePathNode(0.5f));
                helper.add(zenithDecoration);
            }
            count++;
        }
        if (owner.getUseItem().getItem() instanceof ZenithItem) {
            if (power > 3.33f) {
                Playable.play(ZenithSoundRegister.Zenith, owner.level(), owner.position(), owner.getSoundSource());
                Vec3 lookAngle = owner.getLookAngle();
                Vec3 center = owner.getBoundingBox().getCenter();
                Vec3 eyePos = owner.getEyePosition();
                int reach = 32;
                Vec3 endEndPos = eyePos.add(lookAngle.scale(reach));
                EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(owner, eyePos, endEndPos, owner.getBoundingBox().inflate(reach), entity -> entity instanceof LivingEntity living && living.isAlive() && living != owner, reach * reach);
                if (entityHit != null && entityHit.getEntity() instanceof LivingEntity) {
                    endEndPos = entityHit.getLocation();
                }
                TargetCache targetCache = owner.getData(LyraAttachmentRegister.TargetCache);
                Predicate<LivingEntity> predicate = target -> owner != target && !(target instanceof Player player && (player.isSpectator() || player.isCreative()));
                List<LivingEntity> targetList = targetCache.getEntitiesInRadius(endEndPos, 32, predicate);
                LivingEntity best = null;
                if (!targetList.isEmpty()) {
                    int searchRange = 32;
                    double fovAngle = 30;
                    double distanceWeight = 0.2;
                    double bestScore = Double.MAX_VALUE;
                    for (LivingEntity living : targetList) {
                        Vec3 targetPoint = living.getBoundingBox().getCenter();
                        Vec3 toEntity = targetPoint.subtract(center);
                        double angle = Math.toDegrees(Math.acos(toEntity.dot(lookAngle) / toEntity.length()));
                        if (angle <= fovAngle) {
                            // 角度、距离分别归一化到 [0,1]
                            double normalizedAngle = angle / fovAngle;
                            double normalizedDistance = toEntity.length() / searchRange;
                            // 综合分数：按权重合成角度与距离，越小越优先
                            double score = (1.0 - distanceWeight) * normalizedAngle + distanceWeight * normalizedDistance;
                            if (score < bestScore) {
                                bestScore = score;
                                best = living;
                            }
                        }
                    }
                }
                do {
                    power -= 3.33f;
                    Zenith zenith = new Zenith();
                    zenith.setOwner(owner);
                    zenith.setDamage((float) owner.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    zenith.setKnockback(1);
                    RandomSource random = owner.getRandom();
                    if (owner.getMainHandItem().is(ZenithItemRegister.TrueCopperShortsword)) {
                        zenith.renderType = Zenith.RenderType.COPPER_SHORT_SWORD;
                    } else {
                        if (random.nextFloat() < 0.33) {
                            zenith.renderType = Zenith.RenderType.ZENITH;
                        }
                    }
                    float distance = (float) eyePos.distanceTo(endEndPos);
                    Vec3 endPos = endEndPos.offsetRandom(random, distance * 0.1f);
                    if (zenith.renderType != Zenith.RenderType.ZENITH) {
                        List<LivingEntity> radius = zenith.getTargetCache().getEntitiesInRadius(endPos, 10, predicate);
                        if (!radius.isEmpty()) {
                            LivingEntity living = radius.get(random.nextInt(radius.size()));
                            endPos = living.getBoundingBox().getCenter();
                            zenith.target = living;
                        } else {
                            if (best != null) {
                                endPos = best.getBoundingBox().getCenter();
                                zenith.target = best;
                            }
                        }
                    }
                    zenith.offest = endPos.subtract(center);
                    zenith.init(zenith.getRenderNode(1));
                    LyraHelper.get(owner).add(zenith);
                } while (power > 3.33f);
            }
        } else {
            power = 0;
        }
    }

    public void addPower() {
        power = (float) (power + owner.getAttributeValue(Attributes.ATTACK_SPEED));
    }
}
