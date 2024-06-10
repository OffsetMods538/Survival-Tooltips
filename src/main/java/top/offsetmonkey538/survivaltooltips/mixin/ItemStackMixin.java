package top.offsetmonkey538.survivaltooltips.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(
            method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    shift = At.Shift.AFTER
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lcom/google/common/collect/Lists;newArrayList()Ljava/util/ArrayList;"
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"
                    )
            )
    )
    public void survival_tooltips$addCreativeTabToTooltip(CallbackInfoReturnable<List<Text>> cir, @Local List<Text> list, @Local(argsOnly = true) @Nullable PlayerEntity player) {
        for (ItemGroup group : ItemGroups.getGroups()) {
            //noinspection ConstantValue
            if (group.getType() == ItemGroup.Type.SEARCH || !group.contains((ItemStack)(Object)this)) continue;
            list.add(group.getDisplayName().copy().formatted(Formatting.BLUE));
        }
    }
}
