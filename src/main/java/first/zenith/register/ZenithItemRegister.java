package first.zenith.register;

import first.lyra.register.LyraItemRegistries;
import first.zenith.ZenithMod;
import first.zenith.common.item.ZenithItem;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import org.mesdag.portlib.registries.PortDeferredItem;
import org.mesdag.portlib.wrapper.common.PortTags;

public class ZenithItemRegister {

    public static final LyraItemRegistries REGISTRIES = ZenithMod.REGISTRIES;

    public static final PortDeferredItem<ZenithItem> Zenith = REGISTRIES.build("zenith", location -> new ZenithItem(19))
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
            .itemTag(PortTags.Items.DURABILITY_ENCHANTABLE)
            .itemTag(PortTags.Items.WEAPON_ENCHANTABLE)
            .itemTag(PortTags.Items.SWORD_ENCHANTABLE)
            .itemModel(ZenithItemRegister::handheldItem)
            .build();

    public static final PortDeferredItem<ZenithItem> TrueCopperShortsword = REGISTRIES.build("true_copper_shortsword", location -> new ZenithItem(19))
            .itemLanguage("True Copper Shortsword", "真铜短剑")
            .recipeWithLookup((provider, recipeOutput) -> {
                ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ZenithItemRegister.TrueCopperShortsword);
                builder.requires(ZenithItemRegister.Zenith);
                builder.unlockedBy("has_zenith", InventoryChangeTrigger.TriggerInstance.hasItems(ZenithItemRegister.Zenith));
                builder.save(recipeOutput, ZenithMod.rl("true_copper_shortsword"));
            })
            .itemTag(PortTags.Items.DURABILITY_ENCHANTABLE)
            .itemTag(PortTags.Items.WEAPON_ENCHANTABLE)
            .itemTag(PortTags.Items.SWORD_ENCHANTABLE)
            .itemModel(ZenithItemRegister::handheldItem)
            .build();

    private static void handheldItem(ItemModelProvider provider, ResourceLocation item) {
        provider.getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()));
    }

    public static void register() {
    }
}
