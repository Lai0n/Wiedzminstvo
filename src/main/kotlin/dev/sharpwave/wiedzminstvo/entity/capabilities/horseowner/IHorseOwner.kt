package dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner

import net.minecraft.entity.passive.horse.AbstractHorseEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.RegistryKey
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World

interface IHorseOwner {
    var horseNBT: CompoundNBT
    var horseNum: Int
    var storageUUID: String
    var lastSeenPosition: Vector3d
    var lastSeenDim: RegistryKey<World>

    fun createHorseEntity(world: World): AbstractHorseEntity?
    fun setHorse(horse: AbstractHorseEntity, player: PlayerEntity)
    fun clearHorse()
}