package dev.sharpwave.wiedzminstvo.block

import dev.sharpwave.wiedzminstvo.item.IGrowableFlower
import net.minecraft.block.*
import net.minecraft.state.IntegerProperty
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import java.util.*

open class GrowableFlowerBlock(
    properties: Properties,
    override val minSurvivalBrightness: Int,
    override val minGrowingBrightness: Int
) : FlowerBlock(properties), IGrowableFlower {

    override fun canSurvive(state: BlockState, reader: IWorldReader, pos: BlockPos): Boolean {
        return super<IGrowableFlower>.canSurvive(state, reader, pos)
    }

    override fun getMaxAge(): Int {
        return 3
    }

    override fun getAgeProperty(): IntegerProperty {
        return AGE
    }

    override fun getDefaultBlockState(): BlockState {
        return defaultBlockState()
    }

    override fun isRandomlyTicking(state: BlockState): Boolean {
        return super<IGrowableFlower>.isRandomlyTicking(state)
    }

    override fun randomTick(state: BlockState, level: ServerWorld, pos: BlockPos, random: Random) {
        super<IGrowableFlower>.randomTick(state, level, pos, random)
    }

    companion object {
        val AGE = BlockStateProperties.AGE_3
    }
}