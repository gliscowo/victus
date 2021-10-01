package com.glisco.victus.hearts;

import com.glisco.victus.Victus;
import com.glisco.victus.network.VictusPackets;
import com.glisco.victus.util.VictusStatusEffects;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HeartAspectComponent implements Component, AutoSyncedComponent {

    private final PlayerEntity provider;
    private final List<HeartAspect> aspects;

    public HeartAspectComponent(PlayerEntity provider) {
        this.provider = provider;
        this.aspects = new ArrayList<>();
    }

    public boolean acceptsNew() {
        return this.aspects.size() < capacity();
    }

    /**
     * Adds a new aspect to this component, use {@link HeartAspectComponent#acceptsNew()}
     * to test whether it will be accepted
     *
     * @param aspect The aspect to add
     * @return Whether the aspect was added, false if this component is full
     */
    public boolean addAspect(HeartAspect aspect) {
        if (this.aspects.size() >= capacity()) return false;

        this.aspects.add(aspect);

        Victus.ASPECTS.sync(provider);
        return true;
    }

    /**
     * Removes the last aspect from this component
     *
     * @return The aspect that was removed, {@code null} if this component is empty
     */
    public HeartAspect removeAspect() {
        if (this.aspects.size() < 1) return null;

        final var removedAspect = this.aspects.remove(this.aspects.size() - 1);
        Victus.ASPECTS.sync(provider);
        return removedAspect;
    }

    public void tick() {
        final int rechargeRate = provider.hasStatusEffect(VictusStatusEffects.RESURGENCE) ? 2 : 1;

        for (int i = 0; i < effectiveSize(); i++) {
            HeartAspect aspect = this.aspects.get(i);
            if (provider.getHealth() <= i * 2 + 1) return;

            aspect.tick(rechargeRate);
            if (!aspect.active()) return;
        }
    }

    public boolean recharging() {
        for (int i = 0; i < effectiveSize(); i++) {
            if (!aspects.get(i).active()) return true;
        }
        return false;
    }

    /**
     * Instantly recharges all inactive aspects in this component
     * by the given amount of their respective recharge duration
     *
     * @param percentage The percentage of each aspect's recharge duration to skip
     */
    public void rechargeAllByPercentage(float percentage) {
        for (var aspect : aspects) {
            aspect.rechargeByPercentage(percentage);
        }
    }

    /**
     * Damages the aspect at the current index
     * Also recursively backtracks to damage all aspects
     * prior to the given index
     *
     * @param index          The aspect to damage
     * @param source         The {@link DamageSource} that caused this aspect to break
     * @param damage         The damage that was inflicted by said source
     * @param originalHealth The original health the player had before being damaged
     */
    public void damageAspect(int index, DamageSource source, float damage, float originalHealth) {
        var aspect = getAspect(index);
        if (aspect == null) return;

        final int nextIndex = index + 1;
        final var nextAspect = getAspect(nextIndex);
        if (nextAspect != null) damageAspect(nextIndex, source, damage, originalHealth);

        VictusPackets.sendAspectBreak((ServerPlayerEntity) provider, index, aspect.onBroken(source, damage, originalHealth));
    }

    @Nullable
    public HeartAspect getAspect(int index) {
        return index < 0 || index > effectiveSize() - 1 ? null : aspects.get(index);
    }

    public int findFirstIndex(HeartAspect.Type type, Predicate<HeartAspect> filter) {
        for (int i = 0; i < this.effectiveSize(); i++) {
            final var aspect = this.getAspect(i);
            if (aspect.getType() == type && filter.test(aspect)) return i;
        }
        return -1;
    }

    public boolean hasAspect(HeartAspect.Type type, Predicate<HeartAspect> filter) {
        for (var aspect : this.aspects) {
            if (aspect.getType() != type) continue;
            if (!filter.test(aspect)) continue;
            return true;
        }
        return false;
    }

    /**
     * @return The current maximum size of this component
     */
    public int capacity() {
        return (int) (provider.getMaxHealth() / 2);
    }

    /**
     * @return The amount of aspects this component currently has, capped at the current capacity
     */
    public int effectiveSize() {
        return Math.min(aspects.size(), capacity());
    }

    /**
     * @return The actual amount of aspects technically stored in this component, without respecting the capacity cap
     */
    public int realSize() {
        return aspects.size();
    }

    public boolean empty() {
        return this.aspects.size() == 0;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        aspects.clear();

        tag.getList("Aspects", NbtElement.COMPOUND_TYPE).forEach(element -> {

            final var compound = (NbtCompound) element;
            final var typeString = compound.getString("Type");

            final var id = Identifier.tryParse(typeString);
            if (id == null) {
                Victus.getLogger().warn("Tried to load aspect with invalid id {}. Skipping", typeString);
                return;
            }

            final var aspect = HeartAspectRegistry.forId(id, provider);
            if (aspect == null) {
                Victus.getLogger().warn("Failed to load aspect with unknown id {}. Skipping", typeString);
                return;
            }

            aspect.readNbt(compound);
            aspects.add(aspect);
        });
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        final var list = new NbtList();
        aspects.forEach(heart -> list.add(heart.toNbt()));
        tag.put("Aspects", list);
    }
}
