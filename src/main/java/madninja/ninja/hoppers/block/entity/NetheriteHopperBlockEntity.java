package madninja.ninja.hoppers.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import madninja.ninja.hoppers.Main;
import madninja.ninja.hoppers.screen.NetheriteHopperScreenHandler;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.Direction;

public class NetheriteHopperBlockEntity extends HopperBlockEntity implements SidedInventory {
    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 1).toArray();

    private Inventory filterInventory = new SimpleInventory(9);

    private List<ItemStack> getFilters() {
        List<ItemStack> result = new ArrayList<ItemStack>();
        for (int i=0;i<9;i++)
        {
            result.add(filterInventory.getStack(i));
        }

        return result;
    }

    public void scatterFilter() {
        ItemScatterer.spawn(this.getWorld(), this.getPos(), this.filterInventory);
    }

    public boolean isAcceptedByFilter(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return true;

        // No filter allows any item
        List<ItemStack> filters = getFilters();

        if (filters.stream().allMatch(x->x == null || x.isEmpty())) {
            return true;
        }

        return filters.stream().anyMatch(x->x.getItem() == stack.getItem());
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return AVAILABLE_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return this.isAcceptedByFilter(stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.netherite_hopper");
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        for (int i=0;i<9;i++) {
            this.filterInventory.setStack(i, ItemStack.fromTag(tag.getCompound("Filter"+i)));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        for (int i=0;i<9;i++) {
            tag.put("Filter"+i, this.filterInventory.getStack(i).toTag(new CompoundTag()));
        }
        return super.toTag(tag);
    }

    @Override
    public BlockEntityType<?> getType() {
        return Main.NETHERITE_HOPPER_BLOCK_ENTITY_TYPE;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new NetheriteHopperScreenHandler(syncId, playerInventory, this, this.filterInventory);
    }
}