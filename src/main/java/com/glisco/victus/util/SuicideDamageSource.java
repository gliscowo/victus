package com.glisco.victus.util;

import net.minecraft.entity.damage.DamageSource;

public class SuicideDamageSource extends DamageSource {

    protected SuicideDamageSource() {
        super("suicide");
    }

    public static SuicideDamageSource create() {
        return new SuicideDamageSource();
    }
}
