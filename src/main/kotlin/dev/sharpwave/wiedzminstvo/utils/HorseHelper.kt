package dev.sharpwave.wiedzminstvo.utils

import dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner.HorseOwnerProvider
import dev.sharpwave.wiedzminstvo.entity.capabilities.horseowner.IHorseOwner
import dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse.HorseProvider
import dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse.IStoredHorse
import dev.sharpwave.wiedzminstvo.network.main.horse.packets.HorseCapSyncPacket
import dev.sharpwave.wiedzminstvo.world.data.HorsesWorldData
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.common.util.NonNullConsumer
import net.minecraftforge.fml.network.PacketDistributor
import net.minecraftforge.fml.network.PacketDistributor.TargetPoint
import java.util.*


object HorseHelper {
    private val cachedHorses: MutableMap<Entity, LazyOptional<IStoredHorse>> = HashMap()
    fun getOwnerCap(player: PlayerEntity): IHorseOwner? {
        val cap = player.getCapability(HorseOwnerProvider.OWNER_CAPABILITY!!, null)
        return if (cap.isPresent) cap.resolve().get() else null
    }

    fun getHorseCap(horse: Entity): IStoredHorse? {
        var cap = cachedHorses[horse]
        if (cap == null) {
            cap = horse.getCapability(HorseProvider.HORSE_CAPABILITY!!, null)
            cachedHorses[horse] = cap
            cap.addListener(NonNullConsumer {
                cachedHorses.remove(
                    horse
                )
            })
        }
        return if (cap.isPresent) cap.resolve().get() else null
    }

    fun sendHorseUpdateInRange(horse: Entity) {
        val storedHorse = getHorseCap(horse)
        HorseCapSyncPacket.send(PacketDistributor.NEAR.with {
            TargetPoint(
                horse.x,
                horse.y,
                horse.z,
                32.0,
                horse.level.dimension()
            )
        }, HorseCapSyncPacket(horse.id, storedHorse))
    }

    fun sendHorseUpdateToClient(horse: Entity, player: PlayerEntity) {
        val storedHorse = getHorseCap(horse)
        HorseCapSyncPacket.send(
            PacketDistributor.PLAYER.with { player as ServerPlayerEntity? },
            HorseCapSyncPacket(horse.id, storedHorse)
        )
    }

    fun getPlayerFromUUID(uuid: String, world: World): PlayerEntity? {
        val server = world.server
        return server!!.playerList.getPlayer(UUID.fromString(uuid))
    }

    fun setHorseNum(world: ServerWorld, storageId: String, num: Int) {
        world.server.allLevels.forEach { serverWorld ->
            val storedHorses: HorsesWorldData = HorsesWorldData.getInstance(serverWorld)
            storedHorses.addHorseNum(storageId, num)
        }
    }

    fun getHorseNum(world: ServerWorld, storageId: String): Int {
        val storedHorses: HorsesWorldData = HorsesWorldData.getInstance(world)
        return storedHorses.getHorseNum(storageId)
    }

    fun setHorseLastSeen(player: PlayerEntity) {
        val owner = getOwnerCap(player)!!
        owner.lastSeenPosition = player.position()
        owner.lastSeenDim = player.level.dimension()
    }

    fun getWorldData(world: ServerWorld): HorsesWorldData {
        return HorsesWorldData.getInstance(world)
    }
}