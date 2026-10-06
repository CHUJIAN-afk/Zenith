package first.zenith.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import first.zenith.common.item.ZenithItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Shadow
    private boolean startedUsingItem;

    @WrapMethod(method = "aiStep")
    private void lyra$aiStep(Operation<Void> original) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        ItemStack useItem = self.getUseItem();
        if (self.isUsingItem() && useItem.getItem() instanceof ZenithItem) {
            boolean wasUsing = this.startedUsingItem;
            this.startedUsingItem = false;
            original.call();
            this.startedUsingItem = wasUsing;
        } else {
            original.call();
        }
    }
}
