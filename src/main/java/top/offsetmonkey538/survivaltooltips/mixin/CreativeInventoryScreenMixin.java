package top.offsetmonkey538.survivaltooltips.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin {

    @ModifyExpressionValue(
            method = "getTooltipFromItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemGroups;getGroupsToDisplay()Ljava/util/List;"
            )
    )
    @SuppressWarnings("unused")
    public List<ItemGroup> survival_tooltips$removeCreativeTabFromSearchTooltip(List<ItemGroup> original) {
        return List.of();
    }
}
