package com.glisco.victus.item;

import com.glisco.victus.Victus;
import com.glisco.victus.network.VictusPackets;
import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import net.minecraft.item.ItemStack;

public class VictusItemGroup extends OwoItemGroup {

    public VictusItemGroup() {
        super(Victus.id("victus"));
    }

    @Override
    protected void setup() {
        this.addButton(ItemGroupButton.curseforge("https://www.curseforge.com/minecraft/mc-mods/victus"));
        this.addButton(ItemGroupButton.modrinth("https://modrinth.com/mod/victus"));
        this.addButton(ItemGroupButton.discord("https://discord.gg/xrwHKktV2d"));
        this.addButton(ItemGroupButton.github("https://github.com/glisco03/victus"));

        this.addButton(new ItemGroupButton(Icon.of(VictusItems.BLANK_HEART_ASPECT), "remove_first_heart", VictusPackets::requestAspectRemoval));
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(VictusItems.VOID_HEART_ASPECT);
    }
}
