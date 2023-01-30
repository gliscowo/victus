package com.glisco.victus.item;

import com.glisco.victus.Victus;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

public class VictusJournalItem extends Item {

    private static final Identifier VICTUS_GUIDE = Victus.id("victus_guide");

    public VictusJournalItem() {
        super(new OwoItemSettings().maxCount(1).group(Victus.VICTUS_GROUP));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            if (FabricLoader.getInstance().isModLoaded("patchouli")) {
                this.openPatchouliBook(player);
            } else {
                player.sendMessage(
                        Text.literal("You don't currently have ")
                                .append(link("Patchouli", "https://modrinth.com/mod/patchouli"))
                                .append(Text.literal(" installed. You can view "))
                                .append(link("the online Victus Journal", "https://guides.wispforest.io/victus/"))
                                .append(Text.literal(" instead"))
                );
            }
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    private MutableText link(String text, String url) {
        return Text.literal(text).styled(style -> {
            return style.withFormatting(Formatting.BLUE)
                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(url)));
        });
    }

    private void openPatchouliBook(PlayerEntity player) {
        Book book = BookRegistry.INSTANCE.books.get(VICTUS_GUIDE);

        PatchouliAPI.get().openBookGUI((ServerPlayerEntity) player, book.id);
        player.playSound(PatchouliSounds.getSound(book.openSound, PatchouliSounds.BOOK_OPEN), 1, (float) (0.7 + Math.random() * 0.4));
    }
}
