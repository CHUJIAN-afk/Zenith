package first.zenith.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ZenithItem extends SwordItem {

    public ZenithItem(ResourceLocation location, double damage) {
        super(Tiers.NETHERITE, new Item.Properties().attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(location, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()).rarity(Rarity.EPIC).stacksTo(1));
    }
}
