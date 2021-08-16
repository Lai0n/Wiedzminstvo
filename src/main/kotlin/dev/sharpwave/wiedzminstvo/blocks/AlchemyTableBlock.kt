package dev.sharpwave.wiedzminstvo.blocks

import dev.sharpwave.wiedzminstvo.tileentity.AlchemyTableTileEntity
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.ContainerBlock
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.EnchantmentContainer
import net.minecraft.inventory.container.INamedContainerProvider
import net.minecraft.inventory.container.SimpleNamedContainerProvider
import net.minecraft.item.ItemStack
import net.minecraft.particles.ParticleTypes
import net.minecraft.pathfinding.PathType
import net.minecraft.tileentity.EnchantingTableTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.INameable
import net.minecraft.util.IWorldPosCallable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.*

class AlchemyTableBlock(properties: Properties) : ContainerBlock(properties) {
    override fun useShapeForLightOcclusion(state: BlockState): Boolean {
        return true
    }

    override fun getShape(state: BlockState, reader: IBlockReader, pos: BlockPos, selection: ISelectionContext): VoxelShape {
        return SHAPE
    }

    @OnlyIn(Dist.CLIENT)
    override fun animateTick(state: BlockState, level: World, pos: BlockPos, random: Random) {
        super.animateTick(state, level, pos, random)
        for (i in -2..2) {
            var j = -2
            while (j <= 2) {
                if (i > -2 && i < 2 && j == -1) {
                    j = 2
                }
                if (random.nextInt(16) == 0) {
                    for (k in 0..1) {
                        val blockpos = pos.offset(i, k, j)
                        if (level.getBlockState(blockpos).getEnchantPowerBonus(level, blockpos) > 0) {
                            if (!level.isEmptyBlock(pos.offset(i / 2, 0, j / 2))) {
                                break
                            }
                            level.addParticle(
                                ParticleTypes.ENCHANT,
                                pos.x.toDouble() + 0.5,
                                pos.y.toDouble() + 2.0,
                                pos.z.toDouble() + 0.5,
                                (i.toFloat() + random.nextFloat()).toDouble() - 0.5,
                                (k.toFloat() - random.nextFloat() - 1.0f).toDouble(),
                                (j.toFloat() + random.nextFloat()).toDouble() - 0.5
                            )
                        }
                    }
                }
                ++j
            }
        }
    }

    override fun getRenderShape(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun newBlockEntity(reader: IBlockReader): TileEntity {
        return AlchemyTableTileEntity()
    }

    override fun use(state: BlockState, level: World, pos: BlockPos, player: PlayerEntity, hand: Hand, rayTrace: BlockRayTraceResult): ActionResultType {
        return if (level.isClientSide) {
            ActionResultType.SUCCESS
        } else {
            player.openMenu(state.getMenuProvider(level, pos))
            ActionResultType.CONSUME
        }
    }

    override fun getMenuProvider(state: BlockState, level: World, pos: BlockPos): INamedContainerProvider? {
        val tileentity = level.getBlockEntity(pos)
        return if (tileentity is EnchantingTableTileEntity) {
            val itextcomponent = (tileentity as INameable).displayName
            SimpleNamedContainerProvider({ containerId: Int, inventory: PlayerInventory, player: PlayerEntity ->
                EnchantmentContainer(
                    containerId,
                    inventory,
                    IWorldPosCallable.create(level, pos)
                )
            }, itextcomponent)
        } else {
            null
        }
    }

    override fun setPlacedBy(level: World, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomHoverName()) {
            val tileentity = level.getBlockEntity(pos)
            if (tileentity is EnchantingTableTileEntity) {
                tileentity.customName = stack.hoverName
            }
        }
    }

    override fun isPathfindable(state: BlockState, reader: IBlockReader, pos: BlockPos, pathType: PathType): Boolean {
        return false
    }

    companion object {
        protected val SHAPE = box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0)
    }
}