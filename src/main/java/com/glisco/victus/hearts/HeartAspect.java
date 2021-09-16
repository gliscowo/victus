package com.glisco.victus.hearts;

import com.glisco.victus.Victus;
import com.glisco.victus.item.HeartAspectItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.Predicate;

public class HeartAspect implements ItemConvertible {

    public static final Identifier HEART_ATLAS_TEXTURE = Victus.id("textures/gui/hearts.png");
    protected static final Predicate<PlayerEntity> NEVER_UPDATE = p -> false;
    protected static final Predicate<PlayerEntity> ALWAYS_UPDATE = p -> true;

    protected final PlayerEntity player;

    private final Type type;
    private int cooldown = -1;

    public HeartAspect(PlayerEntity player, Type type) {
        this.player = player;
        this.type = type;
    }

    public final NbtCompound toNbt() {
        final var containerNbt = new NbtCompound();
        containerNbt.putString("Type", getType().id.toString());
        containerNbt.putInt("Cooldown", cooldown);

        final var dataNbt = new NbtCompound();
        writeCustomData(dataNbt);
        containerNbt.put("CustomData", dataNbt);

        return containerNbt;
    }

    public final void readNbt(NbtCompound nbt) {
        this.cooldown = nbt.getInt("Cooldown");
        readCustomData(nbt.getCompound("CustomData"));
    }

    protected void readCustomData(NbtCompound nbt) {

    }

    protected void writeCustomData(NbtCompound nbt) {

    }

    public void tick() {
        if (this.cooldown > -1) this.cooldown--;
        else if (this.type.updateCondition.test(player)) update();
    }

    /**
     * Can be implemented for behaviour ran every tick this heart is active
     */
    protected void update() {

    }

    /**
     * Creates an update condition that applies to players below the given absolute health
     */
    protected static Predicate<PlayerEntity> belowHealth(float health) {
        return p -> p.getHealth() <= health;
    }

    /**
     * Creates an update condition that applies to players below the given percentage
     * of their max health
     */
    protected static Predicate<PlayerEntity> belowHealthPercentage(float percentage) {
        return p -> p.getHealth() <= p.getMaxHealth() * percentage;
    }

    /**
     * Called on the client to sync a broken aspect state with the server
     * and play possible client-side effects
     */
    @Environment(EnvType.CLIENT)
    public final void onBrokenClient() {
        if (this.active()) this.handleBreakClient();

        this.cooldown = getRechargeDuration();
    }

    /**
     * Call this to signal that this aspect has received damage
     * Will delegate to implementation for effects and put this
     * aspect on cooldown
     */
    public final void onBroken() {
        if (this.active()) this.handleBreak();

        this.cooldown = this.getRechargeDuration();
    }

    /**
     * @return Whether this aspect is currently ready to apply effects when damaged
     */
    public final boolean active() {
        return this.cooldown == -1;
    }

    /**
     * @return The percentage that this aspect has already recharged
     */
    public final float getRechargeProgress() {
        return cooldown != -1 ? (getRechargeDuration() - cooldown) / (float) getRechargeDuration() : 1;
    }

    /**
     * @return The time this aspect takes to recharge, in ticks
     */
    protected int getRechargeDuration() {
        return type.standardRechargeDuration;
    }

    /**
     * @return The atlas texture this aspect uses and for which the texture index is correct
     */
    public Identifier getAtlas() {
        return HEART_ATLAS_TEXTURE;
    }

    public final Type getType() {
        return type;
    }

    /**
     * Called when this aspect has been broken
     */
    protected void handleBreak() {

    }

    /**
     * Called on the client when this aspect has been broken
     */
    @Environment(EnvType.CLIENT)
    protected void handleBreakClient() {

    }

    /**
     * @return The index of this aspect's texture into the heart atlas
     */
    public int getTextureIndex() {
        return type.textureIndex;
    }

    @Override
    public Item asItem() {
        return HeartAspectItem.getItem(type);
    }


    public static final record Type(Identifier id, int textureIndex, int standardRechargeDuration, Predicate<PlayerEntity> updateCondition, Function<PlayerEntity, HeartAspect> factory) {

        /**
         * @param id The registry ID of this type
         * @param textureIndex The index into the atlas texture where this aspect's texture is located
         * @param standardRechargeDuration The default recharge duration of this aspect type, can be overridden dynamically
         * @param updateCondition The conditions under which to call the {@link HeartAspect#update()} function,
         * @param factory The aspect factory
         *
         * @see HeartAspect#belowHealth(float)
         * @see HeartAspect#belowHealthPercentage(float)
         */
        public Type(Identifier id, int textureIndex, int standardRechargeDuration, Predicate<PlayerEntity> updateCondition, Function<PlayerEntity, HeartAspect> factory) {
            this.id = id;
            this.textureIndex = textureIndex;
            this.standardRechargeDuration = standardRechargeDuration;
            this.updateCondition = updateCondition;
            this.factory = factory;
        }

        /**
         * Convenience constructor that does not take an update condition and uses {@link HeartAspect#NEVER_UPDATE} as the default
         */
        public Type(Identifier id, int textureIndex, int standardRechargeDuration, Function<PlayerEntity, HeartAspect> factory) {
            this(id, textureIndex, standardRechargeDuration, NEVER_UPDATE, factory);
        }

    }
}
