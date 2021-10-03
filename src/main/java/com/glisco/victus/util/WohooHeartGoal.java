package com.glisco.victus.util;

import com.glisco.victus.item.VictusItems;
import com.glisco.victus.mixin.SpellcastingIllagerEntityAccessor;
import com.glisco.victus.network.VictusParticleEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;

import java.util.EnumSet;
import java.util.List;

public class WohooHeartGoal extends Goal {
    protected int spellCooldown;
    protected int startTime;

    private final EvokerEntity evoker;
    public final LookAtItemGoal lookGoal = new LookAtItemGoal();

    private ItemEntity target;

    public WohooHeartGoal(EvokerEntity evoker) {
        this.evoker = evoker;
    }

    @Override
    public boolean canStart() {
        if (evoker.getTarget() != null) {
            return false;
        } else if (evoker.isSpellcasting()) {
            return false;
        } else if (evoker.age < this.startTime) {
            return false;
        } else {
            List<ItemEntity> list = evoker.world.getEntitiesByClass(ItemEntity.class, this.evoker.getBoundingBox().expand(16, 4, 16), entity -> entity.getStack().getItem() == VictusItems.BLANK_HEART_ASPECT);
            if (list.isEmpty()) {
                return false;
            } else {
                this.target = list.get(evoker.world.random.nextInt(list.size()));
                return true;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return target != null && target.isAlive() && this.target.getStack().getItem() == VictusItems.BLANK_HEART_ASPECT;
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public void start() {
        this.spellCooldown = 40;
        ((SpellcastingIllagerEntityAccessor) evoker).victus_setSpellTicks(60);
        this.startTime = evoker.age + 140;

        evoker.playSound(SoundEvents.ENTITY_EVOKER_PREPARE_WOLOLO, 1.0F, 1.0F);
        evoker.setSpell(SpellcastingIllagerEntity.Spell.BLINDNESS);
    }

    @Override
    public void tick() {
        this.spellCooldown--;
        if (this.spellCooldown == 0) {
            target.setStack(new ItemStack(VictusItems.EVOKING_HEART_ASPECT));
            VictusParticleEvents.dispatchPoofParticles((ServerWorld) target.world, target);

            evoker.playSound(((SpellcastingIllagerEntityAccessor) evoker).victus_invokeGetCastSpellSound(), 1.0F, 1.0F);
        }
    }

    public class LookAtItemGoal extends Goal {
        public LookAtItemGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return WohooHeartGoal.this.target != null;
        }

        @Override
        public boolean shouldContinue() {
            return WohooHeartGoal.this.target != null;
        }

        @Override
        public void start() {
            WohooHeartGoal.this.evoker.getNavigation().stop();
        }

        @Override
        public void stop() {
            WohooHeartGoal.this.evoker.setSpell(SpellcastingIllagerEntity.Spell.NONE);
        }

        @Override
        public void tick() {
            if (WohooHeartGoal.this.target != null) {
                WohooHeartGoal.this.evoker.getLookControl().lookAt(WohooHeartGoal.this.target, (float) WohooHeartGoal.this.evoker.getBodyYawSpeed(), (float) WohooHeartGoal.this.evoker.getLookPitchSpeed());
            }
        }
    }
}
