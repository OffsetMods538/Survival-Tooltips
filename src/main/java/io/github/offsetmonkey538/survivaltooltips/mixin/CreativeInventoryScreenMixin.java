package io.github.offsetmonkey538.survivaltooltips.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.List;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin {

    @WrapWithCondition(
            method = "renderTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(ILjava/lang/Object;)V"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Ljava/util/Set;forEach(Ljava/util/function/Consumer;)V"
                    )
            )
    )
    @SuppressWarnings("unused")
    public boolean survival_tooltips$removeCreativeTabFromSearchTooltip(List<Text> instance, int index, Object element) {
        return false;
    }
}
