package first.zenith.register;

import first.lyra.register.LyraItemRegistries;
import first.zenith.ZenithMod;
import first.zenith.common.item.ZenithItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.registries.DeferredItem;

public class ZenithItemRegister {

    public static final LyraItemRegistries REGISTRIES = ZenithMod.REGISTRIES;

    public static final DeferredItem<ZenithItem> Zenith = REGISTRIES.build("zenith", location -> new ZenithItem(location, 19))
            .itemLanguage("Zenith", "天顶剑")
            .itemTag(ItemTags.DURABILITY_ENCHANTABLE)
            .itemTag(ItemTags.WEAPON_ENCHANTABLE)
            .itemTag(ItemTags.SWORD_ENCHANTABLE)
            .itemModel(ItemModelProvider::handheldItem)
            .build();

    public static final DeferredItem<ZenithItem> TrueCopperShortsword = REGISTRIES.build("true_copper_shortsword", location -> new ZenithItem(location, 19))
            .itemLanguage("True Copper Shortsword", "真铜短剑")
            .itemTag(ItemTags.DURABILITY_ENCHANTABLE)
            .itemTag(ItemTags.WEAPON_ENCHANTABLE)
            .itemTag(ItemTags.SWORD_ENCHANTABLE)
            .itemModel(ItemModelProvider::handheldItem)
            .build();

    public static void register() {
    }
}
