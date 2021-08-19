package madninja.ninja.hoppers.mixin;

import madninja.ninja.hoppers.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireBlockMixin {
    @Inject(method = "canRunOnTop", at = @At("HEAD"), cancellable = true)
    private void allowWireOnNetheriteHoppers(BlockView world, BlockPos pos, BlockState floor, CallbackInfoReturnable<Boolean> ci) {
        if (floor.isOf(Main.NETHERITE_HOPPER)) {
            ci.setReturnValue(true);
        }
    }
}