package top.offsetmonkey538.survivaltooltips.mixin;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }


    @Shadow private static ItemGroup selectedTab;

    @Inject(
            method = "getTooltipFromItem",
            at = @At("HEAD"),
            cancellable = true
    )
    @SuppressWarnings("unused")
    public void survival_tooltips$removeCreativeTabFromSearchTooltip(ItemStack stack, CallbackInfoReturnable<List<Text>> cir) {
        if (selectedTab.getType() != ItemGroup.Type.SEARCH || client == null) return;

        cir.setReturnValue(HandledScreen.getTooltipFromItem(this.client, stack));
    }
}
