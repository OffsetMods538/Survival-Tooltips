package top.offsetmonkey538.survivaltooltips.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(
        remap = false,
        value = ItemStack.class
)
public abstract class ItemStackMixin {

    @Group(name = "survival_tooltips$addCreativeTabToTooltip", min = 1, max = 1)
    @Inject(
            method = {
                    // 1.21 and .4 intermediary
                    "method_7950(Lnet/minecraft/class_1792$class_9635;Lnet/minecraft/class_1657;Lnet/minecraft/class_1836;)Ljava/util/List;",
                    // 1.21 and .4 yarn
                    "getTooltip(Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/tooltip/TooltipType;)Ljava/util/List;",
                    // 1.21 and .4 mojmap
                    "getTooltipLines(Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    shift = At.Shift.AFTER
            ),
            slice = @Slice(to = @At(
                    remap = true,
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/tooltip/TooltipType;isAdvanced()Z"
            ))
    )
    public void survival_tooltips$addCreativeTabToTooltip121(CallbackInfoReturnable<List<Text>> cir, @Local List<Text> list, @Local(argsOnly = true) @Nullable PlayerEntity player) {
        survival_tooltips$addCreativeTabToTooltip(list, player);
    }

    @Group(name = "survival_tooltips$addCreativeTabToTooltip", min = 1, max = 1)
    @Inject(
            method = {
                    // 1.20 intermediary
                    "method_7950(Lnet/minecraft/class_1657;Lnet/minecraft/class_1836;)Ljava/util/List;",
                    // 1.20 yarn
                    "getTooltip(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/item/TooltipContext;)Ljava/util/List;",
                    // 1.20 mojmap
                    "getTooltipLines(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    shift = At.Shift.AFTER
            ),
            slice = @Slice(to = @At(
                    remap = true,
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"
            ))
    )
    public void survival_tooltips$addCreativeTabToTooltip120(CallbackInfoReturnable<List<Text>> cir, @Local List<Text> list, @Local(argsOnly = true) @Nullable PlayerEntity player) {
        survival_tooltips$addCreativeTabToTooltip(list, player);
    }

    @Unique
    public void survival_tooltips$addCreativeTabToTooltip(List<Text> list, @Nullable PlayerEntity player) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        if (!(player instanceof ClientPlayerEntity clientPlayer)) return;

        ItemGroups.updateDisplayContext(clientPlayer.networkHandler.getEnabledFeatures(), client.options.getOperatorItemsTab().getValue(), clientPlayer.getWorld().getRegistryManager());
        for (ItemGroup group : ItemGroups.getGroups()) {
            //noinspection ConstantValue
            if (group.getType() == ItemGroup.Type.SEARCH || !group.contains((ItemStack)(Object)this)) continue;
            list.add(group.getDisplayName().copy().formatted(Formatting.BLUE));
        }
    }
}
