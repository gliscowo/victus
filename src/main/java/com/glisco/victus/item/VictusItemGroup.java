package com.glisco.victus.item;

import com.glisco.owo.itemgroup.Icon;
import com.glisco.owo.itemgroup.OwoItemGroup;
import com.glisco.owo.itemgroup.gui.ItemGroupButton;
import com.glisco.victus.Victus;
import com.glisco.victus.network.VictusPackets;
import net.minecraft.item.ItemStack;

public class VictusItemGroup extends OwoItemGroup {

    public VictusItemGroup() {
        super(Victus.id("victus"));
    }

    @Override
    protected void setup() {
        this.addButton(ItemGroupButton.discord("https://discord.gg/xrwHKktV2d"));
        this.addButton(ItemGroupButton.github("https://github.com/glisco03/victus"));

        this.addButton(new ItemGroupButton(Icon.of(VictusItems.BLANK_HEART_ASPECT), "remove_first_heart", VictusPackets::requestAspectRemoval));
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(VictusItems.TOTEM_HEART_ASPECT);
    }
}
