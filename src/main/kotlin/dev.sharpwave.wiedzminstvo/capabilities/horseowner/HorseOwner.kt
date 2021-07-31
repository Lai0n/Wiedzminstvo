package dev.sharpwave.wiedzminstvo.capabilities.horseowner

import dev.sharpwave.wiedzminstvo.WiedzminstvoMod
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.HorseProvider
import dev.sharpwave.wiedzminstvo.capabilities.storedhorse.IStoredHorse
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.horse.AbstractHorseEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import java.util.*


class HorseOwner : IHorseOwner {
    override var horseNum = 0
    override var horseNBT: CompoundNBT = CompoundNBT()
    override var storageUUID: String = ""
    override var lastSeenDim: RegistryKey<World> =
        RegistryKey.func_240903_a_(Registry.WORLD_KEY, ResourceLocation("overworld"))
    override var lastSeenPosition = Vector3d.ZERO

    override fun createHorseEntity(world: World): AbstractHorseEntity? {
        val type: Optional<EntityType<*>> = EntityType.readEntityType(horseNBT)
        if (type.isPresent) {
            val entity = type.get().create(world)
            if (entity is AbstractHorseEntity) {
                entity.read(horseNBT)
                horseNum++
                val cap = entity.getCapability(HorseProvider.HORSE_CAPABILITY, null)
                if (cap.isPresent) {
                    cap.resolve().get().horseNum = horseNum
                    entity.setUniqueId(UUID.randomUUID())
                    entity.extinguish()
                    entity.hurtTime = 0
                }
                return entity
            }
            WiedzminstvoMod.logger.error("The entity with NBT $horseNBT wasn't a horse somehow?...")
        }
        return null
    }

    override fun setHorse(horse: AbstractHorseEntity, player: PlayerEntity) {
        storageUUID = UUID.randomUUID().toString()
        val cap = horse.getCapability(HorseProvider.HORSE_CAPABILITY, null)
        cap.ifPresent { storedHorse: IStoredHorse? ->
            storedHorse!!.horseNum = horseNum
            storedHorse.isOwned = true
            storedHorse.ownerUUID = player!!.gameProfile.id.toString()
            storedHorse.storageUUID = storageUUID
            val tag = horse.serializeNBT()
            horseNBT = tag
        }
    }

    override fun clearHorse() {
        horseNum = 0
        horseNBT = CompoundNBT()
        storageUUID = ""
        lastSeenDim = RegistryKey.func_240903_a_(Registry.WORLD_KEY, ResourceLocation("overworld"))
        lastSeenPosition = Vector3d.ZERO
    }
}