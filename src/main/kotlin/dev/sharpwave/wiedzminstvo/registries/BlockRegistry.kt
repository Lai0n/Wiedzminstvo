package dev.sharpwave.wiedzminstvo.registries

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.blocks.AlchemyFlowerBlock
import net.minecraft.block.Block
import net.minecraft.potion.Effects
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.eventbus.KotlinEventBus
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object BlockRegistry : IForgeRegistry {
    private val BLOCKS : KDeferredRegister<Block> = KDeferredRegister(ForgeRegistries.BLOCKS, WiedzminstvoMod.MODID)

    override fun register(bus: KotlinEventBus) {
        BLOCKS.register(bus)
    }

    val ARENARIA by BLOCKS.registerObject("arenaria") { AlchemyFlowerBlock.make(Effects.HEAL, 1) }
    val BEGGARTICK by BLOCKS.registerObject("beggartick_blossoms") { AlchemyFlowerBlock.make() }
    val BISON_GRASS by BLOCKS.registerObject("bison_grass") { AlchemyFlowerBlock.make() }
    val BLUE_LOTUS by BLOCKS.registerObject("blue_lotus") { AlchemyFlowerBlock.makeTall() }
    val WINTER_CHERRY by BLOCKS.registerObject("winter_cherry") { AlchemyFlowerBlock.makeTall() }
}