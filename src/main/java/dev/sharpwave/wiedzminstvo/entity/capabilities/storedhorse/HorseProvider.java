package dev.sharpwave.wiedzminstvo.entity.capabilities.storedhorse;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ConstantConditions")
public class HorseProvider implements ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(IStoredHorse.class)
    public static final Capability<IStoredHorse> HORSE_CAPABILITY = null;

    private final IStoredHorse instance;

    {
        assert HORSE_CAPABILITY != null;
        instance = HORSE_CAPABILITY.getDefaultInstance();
    }

    @Override
    public CompoundNBT serializeNBT() {
        assert HORSE_CAPABILITY != null;
        return (CompoundNBT) HORSE_CAPABILITY.getStorage().writeNBT(HORSE_CAPABILITY, instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        HORSE_CAPABILITY.getStorage().readNBT(HORSE_CAPABILITY, instance, null, nbt);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side)
    {
        return cap == HORSE_CAPABILITY ? (LazyOptional<T>) LazyOptional.of(() -> instance) : LazyOptional.empty();
    }

}