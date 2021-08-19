package dev.sharpwave.wiedzminstvo.registry

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.block.AlchemyTableBlock
import dev.sharpwave.wiedzminstvo.item.AlchemyItem
import dev.sharpwave.wiedzminstvo.item.ModItemGroup
import dev.sharpwave.wiedzminstvo.item.PotionItem
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.TallBlockItem
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object ItemRegistry : IForgeRegistry {
    private val ITEMS : KDeferredRegister<Item> = KDeferredRegister(ForgeRegistries.ITEMS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        ITEMS.register(bus)
    }

    val ALCHEMY_TABLE by ITEMS.registerObject("alchemy_table") { registerBlock(BlockRegistry.ALCHEMY_TABLE, ModItemGroup.TAB_ALCHEMY) }
    val ALCHEMY_POTION by ITEMS.registerObject("alchemy_potion") { PotionItem() }
    val ALCHEMISTS_POWDER by ITEMS.registerObject("alchemists_powder") { AlchemyItem() }
    val AETHER by ITEMS.registerObject("aether") { AlchemyItem() }
    val ALCHEMY_PASTE by ITEMS.registerObject("alchemy_paste") { AlchemyItem() }
    val ALCOHEST by ITEMS.registerObject("alcohest") { AlchemyItem() }
    val DARK_ESSENCE by ITEMS.registerObject("dark_essence") { AlchemyItem() }
    val DWARVEN_SPIRIT by ITEMS.registerObject("dwarven_spirit") { AlchemyItem() }
    val FIFTH_ESSENCE by ITEMS.registerObject("fifth_essence") { AlchemyItem() }
    val ARENARIA by ITEMS.registerObject("arenaria") { registerBlock(BlockRegistry.ARENARIA, ModItemGroup.TAB_ALCHEMY) }
    // TODO: Maybe change beggartick and bison grass to flower or make implementation which also has offsetting as flower because it's weird how it is in middle of the block
    val BEGGARTICK by ITEMS.registerObject("beggartick_blossoms") { registerBlock(BlockRegistry.BEGGARTICK, ModItemGroup.TAB_ALCHEMY) }
    val BISON_GRASS by ITEMS.registerObject("bison_grass") { registerBlock(BlockRegistry.BISON_GRASS, ModItemGroup.TAB_ALCHEMY) }
    val BLUE_LOTUS by ITEMS.registerObject("blue_lotus") { registerTallBlock(BlockRegistry.BLUE_LOTUS, ModItemGroup.TAB_ALCHEMY) }
    val WINTER_CHERRY by ITEMS.registerObject("winter_cherry") { registerTallBlock(BlockRegistry.WINTER_CHERRY, ModItemGroup.TAB_ALCHEMY) }

    private fun registerBlock(block: Block, tab: ItemGroup): BlockItem {
        return BlockItem(block, Item.Properties().tab(tab))
    }
    private fun registerTallBlock(block: Block, tab: ItemGroup): BlockItem {
        return TallBlockItem(block, Item.Properties().tab(tab))
    }
}