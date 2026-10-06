package first.zenith.register;

import first.lyra.register.LyraItemRegistries;
import first.zenith.ZenithMod;
import first.zenith.common.item.ZenithItem;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.registries.DeferredItem;

public class ZenithItemRegister {

    public static final LyraItemRegistries REGISTRIES = ZenithMod.REGISTRIES;

    public static final DeferredItem<ZenithItem> Zenith = REGISTRIES.build("zenith", location -> new ZenithItem(location, 19))
            .itemLanguage("Zenith", "天顶剑")
            .recipeWithLookup((provider, recipeOutput) -> {
                ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ZenithItemRegister.Zenith);
                builder.requires(ZenithItemRegister.TrueCopperShortsword);
                builder.unlockedBy("has_true_copper_shortsword", InventoryChangeTrigger.TriggerInstance.hasItems(ZenithItemRegister.TrueCopperShortsword));
                builder.save(recipeOutput, ZenithMod.rl("zenith"));
            })
            .lootTable(ResourceLocation.withDefaultNamespace("chests/end_city_treasure"), table -> LootPool.lootPool()
                    .add(LootItem.lootTableItem(ZenithItemRegister.Zenith)
                                 .when(LootItemRandomChanceCondition.randomChance(0.01F)))
                    .build())
            .itemTag(ItemTags.DURABILITY_ENCHANTABLE)
            .itemTag(ItemTags.WEAPON_ENCHANTABLE)
            .itemTag(ItemTags.SWORD_ENCHANTABLE)
            .itemModel(ItemModelProvider::handheldItem)
            .build();

    public static final DeferredItem<ZenithItem> TrueCopperShortsword = REGISTRIES.build("true_copper_shortsword", location -> new ZenithItem(location, 19))
            .itemLanguage("True Copper Shortsword", "真铜短剑")
            .recipeWithLookup((provider, recipeOutput) -> {
                ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ZenithItemRegister.TrueCopperShortsword);
                builder.requires(ZenithItemRegister.Zenith);
                builder.unlockedBy("has_zenith", InventoryChangeTrigger.TriggerInstance.hasItems(ZenithItemRegister.Zenith));
                builder.save(recipeOutput, ZenithMod.rl("true_copper_shortsword"));
            })
            .itemTag(ItemTags.DURABILITY_ENCHANTABLE)
            .itemTag(ItemTags.WEAPON_ENCHANTABLE)
            .itemTag(ItemTags.SWORD_ENCHANTABLE)
            .itemModel(ItemModelProvider::handheldItem)
            .build();

    public static void register() {
    }
}
