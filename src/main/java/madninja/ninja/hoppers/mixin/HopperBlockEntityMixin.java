package madninja.ninja.hoppers.mixin;

import madninja.ninja.hoppers.block.entity.NetheriteHopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import net.minecraft.block.BlockState;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    @Redirect(method = "insert", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 0))
    private boolean filterOutput(ItemStack stack) {
        if (stack.isEmpty()) return true;
        if ((Object) this instanceof NetheriteHopperBlockEntity) {
            NetheriteHopperBlockEntity goldenHopper = (NetheriteHopperBlockEntity) (Object) this;
            if (!goldenHopper.isAcceptedByFilter(stack)) return true;
        }

        return false;
    }

    @Inject(method = "extract(Lnet/minecraft/block/entity/Hopper;Lnet/minecraft/inventory/Inventory;ILnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private static void filterInventoryInput(Hopper hopper, Inventory inventory, int slot, Direction side, CallbackInfoReturnable<Boolean> ci) {
        if (hopper instanceof NetheriteHopperBlockEntity) {
            NetheriteHopperBlockEntity netheriteHopper = (NetheriteHopperBlockEntity) hopper;
            if (!netheriteHopper.isAcceptedByFilter(inventory.getStack(slot))) {
                ci.setReturnValue(false);
            }
        }
    }

    @Inject(method = "extract(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/ItemEntity;)Z", at = @At("HEAD"), cancellable = true)
    private static void filterItemEntityInput(Inventory inventory, ItemEntity itemEntity, CallbackInfoReturnable<Boolean> ci) {
        if (inventory instanceof NetheriteHopperBlockEntity) {
            NetheriteHopperBlockEntity netheriteHopper = (NetheriteHopperBlockEntity) inventory;
            if (!netheriteHopper.isAcceptedByFilter(itemEntity.getStack())) {
                ci.setReturnValue(false);
            }
        }
    }
}
