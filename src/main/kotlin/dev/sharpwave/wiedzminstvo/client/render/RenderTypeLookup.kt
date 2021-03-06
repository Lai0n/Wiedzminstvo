package dev.sharpwave.wiedzminstvo.client.render

import dev.sharpwave.wiedzminstvo.registries.BlockRegistry
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup as RTL

object RenderTypeLookup {
    fun register() {
        val renderCutout = RenderType.cutout()
        RTL.setRenderLayer(BlockRegistry.ARENARIA, renderCutout)
        RTL.setRenderLayer(BlockRegistry.BEGGARTICK, renderCutout)
        RTL.setRenderLayer(BlockRegistry.BISON_GRASS, renderCutout)
        RTL.setRenderLayer(BlockRegistry.BLUE_LOTUS, renderCutout)
        RTL.setRenderLayer(BlockRegistry.WINTER_CHERRY, renderCutout)
    }
}