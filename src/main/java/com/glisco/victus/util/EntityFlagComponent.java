package com.glisco.victus.util;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public class EntityFlagComponent implements Component {

    private int flags = 0;

    public void setFlag(int flag) {
        flags |= flag;
    }

    public void clearFlags() {
        flags = 0;
    }

    public boolean flagSet(int flag) {
        return (flags & flag) != 0;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        flags = tag.getInt("Flags");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("Flags", flags);
    }
}
